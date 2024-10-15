package com.eval.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;

import com.base.common.utils.StringUtils;
import com.eval.pojo.EvalIndexModel;
import com.eval.pojo.EvalNodeEvaluation;
import com.eval.pojo.EvalNodeIndexEvaluation;
import com.eval.pojo.EvalObjEvaluation;
import com.eval.pojo.EvalObjIndexModel;
import com.eval.pojo.EvalObjNodeIndexInput;
import com.eval.pojo.EvalRating;
import com.eval.pojo.Fuzzy;
import com.eval.pojo.IndexImportance;
import com.eval.pojo.IndexMembership;
import com.eval.pojo.NodeTypeWeight;
import com.eval.pojo.ObjNodeTypeEvaluation;
import com.eval.pojo.WeightVector;
import com.eval.vo.EvalNodeIndexEvaluationVo;
import com.eval.vo.EvalResult;
import com.eval.vo.NodeTypeEvalVo;

import javax.swing.*;

@Service
public class FuzzyEvaluation {

    @Autowired
    EvalIndexModelService indexModelService;

    @Autowired
    IndexImportanceService importanceService;

    @Autowired
    EvalObjIndexModelService objIndexModelService;

    @Autowired
    EvalObjNodeIndexInputService objNodeIndexLinkService;

    @Autowired
    EvalRatingService rateService;

    @Autowired
    IndexMembershipService indexMembershipService;

    @Autowired
    EvalNodeIndexEvaluationService nodeIndexEvaluationService;

    @Autowired
    EvalNodeEvaluationService nodeEvaluationService;

    @Autowired
    EvalObjEvaluationService objEvalService;

    @Autowired
    ObjNodeTypeEvaluationService nodeTypeService;

    private static Double gauss(double x, double mu, double sigma) {
        return Math.pow(Math.E, (-(x - mu) * (x - mu)) / (2 * sigma * sigma));
    }

    private static Double trigonometric(double x, double a, double b, double c) {
        if (x < a)
            return 0.0;
        else if (x > a && x < b)
            return (x - a) / (b - a);
        else if (x == b)
            return 1.0;
        else if (x > b && x < c)
            return (x - b) / (c - b);
        else if (x > c)
            return 0.0;
        else
            return 0.0;
    }

    // do fuzzy comprehensive evaluation.
    // a is the  weight vector ，b is the commit vector
    private Double[] mult(Double[] a, Double[][] b) throws Exception {
        int M = a.length;
        int N = b.length;
        int L = b[0].length;
        Double[] c = new Double[M];
        for (int i = 0; i < M; i++) {
            c[i] = 0.0;
        }
        if (M != L)
            throw new Exception("行向量与列向量长度不相等");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                c[i] += b[i][j] * a[j];
            }
        }
        return c;
    }

    public Double[] weightXMatrix(Fuzzy f) throws Exception {
        return mult(f.getA(), f.getB());
    }

    // compute weights of indexes with the importances.
    public WeightVector produceWeightVector(double[][] importances) {
        WeightVector w = new WeightVector();
        Matrix A = DenseMatrix.Factory.importFromArray(importances);
        // System.out.print("A:");
        // System.out.println(A);
        Matrix[] eigenValueDecomposition = A.eig();
        Matrix eigen_vector = eigenValueDecomposition[0];
        Matrix eigen_value = eigenValueDecomposition[1];
        double[][] eigValue = eigen_value.toDoubleArray();
        double[][] eigVector = eigen_vector.toDoubleArray();

        // double[][] eigValue = Eig.matrixEigValue(importances, 10);
        // double[][] eigVector = Eig.matrixEigVector(importances, 10);

        double f_max = 0;
        int f_max_r_ = 0;
        int f_max_c_ = 0;
        for (int i = 0; i < eigValue.length; i++) {
            for (int j = 0; j < eigValue[i].length; j++) {
                if (eigValue[i][j] > f_max) {
                    f_max = eigValue[i][j];
                    f_max_r_ = i;
                    f_max_c_ = j;
                }
            }
        }

        double[] weight = new double[eigVector.length];
        double v_sum = 0;
        for (int k = 0; k < eigVector.length; k++) {
            weight[k] = eigVector[k][f_max_c_];
            v_sum += weight[k];
        }

        for (int h = 0; h < weight.length; h++) {
            weight[h] /= v_sum;
        }

        w.setEigValue(eigValue);
        w.setEigVector(eigVector);
        w.setFmax(f_max);
        w.setC_(f_max_c_);
        w.setR_(f_max_r_);
        w.setWeight(weight);

        return w;

    }

    // 输入指标字符串和重要性矩阵版本，输出指标权重向量
    public WeightVector calNodeTypeWeights(String[] indexList, String ver) {
        // List<EvalIndexModel> indexs =
        // indexModelService.selectByIndexModelAndNodeType(indexModel, nodeType);
        double[][] imp_mat = new double[indexList.length][indexList.length];
        boolean inComplete = false;
        for (int i = 0; i < indexList.length; i++) {
            IndexImportance ii = new IndexImportance();
            ii.setIndexFirst(indexList[i]);
            ii.setVersion(ver);
            List<IndexImportance> iml = importanceService.selectByObj(ii);
            Map<String, Double> map = new HashMap<String, Double>();
            for (IndexImportance im : iml) {
                map.put(im.getIndexSecond(), im.getImportance());
            }
            // 这里应该是判断错误
            if (map.size() < indexList.length) {
                inComplete = true;
                break;
            }
            // 将重要度存入到矩阵中
            for (int j = 0; j < indexList.length; j++) {
                imp_mat[i][j] = map.get(indexList[j]);
            }
        }
        // 这里出现了问题  !!!!
        // System.out.println(imp_mat.toString());
        WeightVector w = new WeightVector();
        if (inComplete) {
            w.setDescription("the importance matrix is incomplete.");
        } else {
            w = produceWeightVector(imp_mat);
            // System.out.println(w.toString());
        }
        w.setImportances(imp_mat);
        return w;
    }

    // 根据指标模型和重要性矩阵版本计算模型指标权重
    public List<NodeTypeWeight> calWeightByIndexModel(EvalObjIndexModel objIndexModel) {
        //获取的index版本
        String indexModel = objIndexModel.getIndexModel();
        System.out.println("----------------------------------"+indexModel);
        //获取 重要性矩阵的版本
        String ver = objIndexModel.getImportanceVersion();
        System.out.println("----------------------------------"+ver);
        // 分类别获取 权重
        List<NodeTypeWeight> res = new ArrayList<NodeTypeWeight>();
        // 获取相应的指标
        List<EvalIndexModel> indexs = indexModelService.selectByIndexModel(indexModel);
        Map<String, List<EvalIndexModel>> map = new HashMap<String, List<EvalIndexModel>>();
        for (EvalIndexModel ind : indexs) {
            //如果已经存在，则直接加入
            if (map.containsKey(ind.getNodeType())) {
                map.get(ind.getNodeType()).add(ind);
            } else { //不存在就创造
                List<EvalIndexModel> ilist = new ArrayList<EvalIndexModel>();
                ilist.add(ind);
                map.put(ind.getNodeType(), ilist);
            }
        }

        for (Map.Entry<String, List<EvalIndexModel>> entry : map.entrySet()) {
            int cntUpdated = 0;
            NodeTypeWeight ntw = new NodeTypeWeight();

            String nodeType = entry.getKey();
            ntw.setNodeType(nodeType);
            List<EvalIndexModel> indObjList = entry.getValue();

            String[] indexList = new String[indObjList.size()];
            boolean hasNullIndex = false;
            for (int i = 0; i < indObjList.size(); i++) {
                if (null == indObjList.get(i).getIndexCode()) {
                    // System.out.println("There are null index");
                    hasNullIndex = true;
                    break;
                } else
                    indexList[i] = indObjList.get(i).getIndexCode();
            }
            if (hasNullIndex) {
                // res.put(nodeType, "there are null index");
                ntw.setDescription("there are null index");
                res.add(ntw);
                continue;
            }
            WeightVector w = calNodeTypeWeights(indexList, ver);
            double[] weight = w.getWeight();
            ntw.setWeight(w);
            if (null != w.getWeight()) {
                System.out.println("------------------------ no weight !!!!!!!!!!!!!!!!!!!!!!!");
                // res.put(nodeType, w.toString());
                ntw.setIsWeighted(true);
                for (int k = 0; k < weight.length; k++) {
                    EvalIndexModel ei = new EvalIndexModel();
                    ei.setId(indObjList.get(k).getId());
                    ei.setWeight(weight[k]);
                    int cnt = indexModelService.update(ei);
                    cntUpdated += cnt;
                }
            }
            ntw.setCntUpdated(cntUpdated);
            res.add(ntw);
        }
        return res;
    }

    // 1. 根据指标输入值,隶属度函数,评级,算出指标隶属度向量, 更新 eval_node_index_evaluation 表
    // 主要内容！！！！
    public List<EvalNodeIndexEvaluation> calIndexMembership(EvalObjIndexModel instance) {
        // 获取objIndexModel
        AtomicReference<EvalObjIndexModel> indexModel = new AtomicReference<>(objIndexModelService.selectByInstance(instance.getEvalInstance()));

        // 从indexModel中的 指标版本，然后获取到所有的评估指标
        List<EvalIndexModel> eimList = indexModelService.selectByIndexModel(indexModel.get().getIndexModel());

        // 制造一个map，以指标名称 indexCode为键
        Map<String, EvalIndexModel> indexModelmap = new HashMap<String, EvalIndexModel>();
        for (EvalIndexModel e : eimList) {
            indexModelmap.put(e.getIndexCode(), e);
        }
        // 获取评估等级
        List<EvalRating> rateList = rateService.selectByVersion(indexModel.get().getRateVersion());

        // 这里获取的列表中，会有父子级别的区别
        List<EvalObjNodeIndexInput> objNoInList = objNodeIndexLinkService
                .selectByInstance(instance.getEvalInstance());

        List<EvalNodeIndexEvaluation> updated = new ArrayList<EvalNodeIndexEvaluation>();

        //判断inputValue是否有值，或者indexCode是否存在
        for (EvalObjNodeIndexInput inobj : objNoInList) {
            if (null == inobj.getInputValue() || null == inobj.getIndexCode()) {
                EvalNodeIndexEvaluation u = new EvalNodeIndexEvaluation();
                CopyObjectUtil.copyProperties(inobj, u, true);
                u.setInputValue(null);
                u.setDescription("input value or index code is null");
                updated.add(u);
                continue;
            }

            // 获得每个 index 的 隶属度函数类型 f
            String f = indexModelmap.get(inobj.getIndexCode()).getMembership();
            String model = indexModelmap.get(inobj.getIndexCode()).getIndexModel();

            //遍历 inputValue表中的指标条目，然后将其转化为evaluation表中的数据
            for (EvalRating r : rateList) {
                //System.out.println("当前等级"+r);
                EvalNodeIndexEvaluation enieval = new EvalNodeIndexEvaluation();
                //复制参数
                enieval.setEvalInstance(inobj.getEvalInstance());
                enieval.setNodeCode(inobj.getNodeCode());
                enieval.setIndexCode(inobj.getIndexCode());
                enieval.setRating(r.getCode());

                // ----------- 问题出现： membership表中只有两个参数的值
                IndexMembership mem = new IndexMembership();
                mem.setIndexCode(inobj.getIndexCode());
                mem.setRatingCode(r.getCode());
                mem.setMembershipFunction(f);
                List<IndexMembership> memberobj = indexMembershipService.selectByCondition(mem);
                // 这里判断了是否能够在membership表中查询到相应的函数信息，如何没有，则直接添加description提醒
                if (memberobj.size() < 1) {
                    enieval.setDescription(
                            String.format("no membership function for %s with rating %s", f, r.getCode()));
                    updated.add(enieval);
                    continue;
                }

                List<EvalNodeIndexEvaluation> evalList = nodeIndexEvaluationService.selectByCondition(enieval);
                CopyObjectUtil.copyProperties(inobj, enieval, true);
                enieval.setId(null);
                enieval.setMembership(f);
                enieval.setIndexModel(model);

                if (f.equals("gauss") && null != memberobj.get(0).getParam1()) {
                    enieval.setEvalValue(
                            gauss(inobj.getInputValue(),
                                    memberobj.get(0).getParam1(),
                                    memberobj.get(0).getParam2()));
                } else if (f.equals("triangle") && null != memberobj.get(0).getParam1()) {
                    enieval.setEvalValue(
                            trigonometric(inobj.getInputValue(),
                                    memberobj.get(0).getParam1(),
                                    memberobj.get(0).getParam2(),
                                    memberobj.get(0).getParam3()));
                } else {
                    enieval.setDescription(f + " is not supported, or its parameters is null.");
                    updated.add(enieval);
                    continue;
                }

                int cnt = 0;
                if (evalList.size() > 0) {
                    enieval.setId(evalList.get(0).getId());
                    cnt = nodeIndexEvaluationService.update(enieval);
                    //System.out.println("更新成功");
                    enieval.setDescription(String.format("update %d records", cnt));
                    updated.add(enieval);

                } else {
                    //System.out.println("插入成功");
                    cnt = nodeIndexEvaluationService.insert(enieval);
                    enieval.setDescription(String.format("insert %d records", cnt));
                    updated.add(enieval);
                }

            }
        }
        //System.out.println(updated.size());
        return updated;
    }

    // 节点获得评估向量的过程
    public List<EvalNodeEvaluation> calNodeFuzzy_new(EvalObjIndexModel instance){
        List<EvalNodeEvaluation> nodeEvalList;
        nodeEvalList = new ArrayList<>();
        //创建复制模版
        EvalNodeIndexEvaluation nodeIndexFuzzy = new EvalNodeIndexEvaluation();
        CopyObjectUtil.copyProperties(instance,nodeIndexFuzzy,true);
        List<EvalNodeIndexEvaluation> nodeIndexFuzzyList = nodeIndexEvaluationService.selectByCondition(nodeIndexFuzzy);

        //获取参数列表和相应的权重参数
        EvalObjIndexModel indexModel = objIndexModelService.selectByInstance(nodeIndexFuzzy.getEvalInstance());
        List<EvalIndexModel> eimList = indexModelService.selectByIndexModel(indexModel.getIndexModel());

        //创建model map 用于间接表查询
        Map<String,EvalIndexModel> indexModelMap = new HashMap<String,EvalIndexModel>();
        for (EvalIndexModel e:eimList){
            indexModelMap.put(e.getIndexCode(),e);
        }
        // 直接将权重乘进去
        for (EvalNodeIndexEvaluation e : nodeIndexFuzzyList) {
            double w = indexModelMap.get(e.getIndexCode()).getWeight();
            if (StringUtils.isEmpty(String.valueOf(w))) {
                EvalNodeEvaluation nodee = new EvalNodeEvaluation();
                nodee.setDescription(
                        String.format("weight of index %s == %f, %s", e.getIndexCode(), w, String.valueOf(w)));
                nodeEvalList.add(nodee);
                //考虑在这里直接把向量加进去
                nodeEvaluationService.insert(nodee);
                return nodeEvalList;
            }
            e.setEvalValue(e.getEvalValue() * w);
        }

        // 制造一个map，以节点+评级为键,以指标向量为值（列表）
        Map<String, List<EvalNodeIndexEvaluation>> indexFuzzyMap = new HashMap<String, List<EvalNodeIndexEvaluation>>();
        for (EvalNodeIndexEvaluation e : nodeIndexFuzzyList) {
            //检查该节点和评级是否已经存在于map之中
            if (indexFuzzyMap.containsKey(e.getNodeCode() + e.getRating())) {
                //如果存在就取出对应键的值，并把e放入到该值中
                indexFuzzyMap.get(e.getNodeCode() + e.getRating()).add(e);
            } else {
                //如果不存在就创建新的
                List<EvalNodeIndexEvaluation> el = new ArrayList<EvalNodeIndexEvaluation>();
                el.add(e);
                indexFuzzyMap.put(e.getNodeCode() + e.getRating(), el);
            }
        }

        for(Map.Entry<String, List<EvalNodeIndexEvaluation>> entry : indexFuzzyMap.entrySet()){
            EvalNodeEvaluation evalNode = new EvalNodeEvaluation();
            evalNode.setEvalInstance(nodeIndexFuzzy.getEvalInstance());
            evalNode.setNodeCode(entry.getValue().get(0).getNodeCode());
            evalNode.setRating(entry.getValue().get(0).getRating());
            List<EvalNodeEvaluation> nodeList = nodeEvaluationService.selectByCondition(evalNode);

            evalNode.setEvalValue(0.0);
            //遍历map中每一项的数据
            for(EvalNodeIndexEvaluation e : entry.getValue()){
                if(StringUtils.isNotEmpty(String.valueOf(e.getEvalValue())))
                    evalNode.setEvalValue(evalNode.getEvalValue()+e.getEvalValue());
                else {
                    evalNode.setDescription(
                            String.format("index %s eval value == %f", e.getIndexCode(), e.getEvalValue()));
                    nodeEvalList.add(evalNode);
                    return nodeEvalList;
                }
            }
            evalNode.setEvalObjCode(entry.getValue().get(0).getEvalObjCode());
            // nodeEval.setIndexModel(nodeIndexFuzzy.getIndexModel());
            int cnt = 0;
            if (nodeList.size() > 0) {
                evalNode.setId(nodeList.get(0).getId());
                cnt = nodeEvaluationService.update(evalNode);
                evalNode.setDescription(String.format("update %d recordes", cnt));
                nodeEvalList.add(evalNode);
            } else {
                cnt = nodeEvaluationService.insert(evalNode);
                evalNode.setDescription(String.format("insert %d recordes", cnt));
                nodeEvalList.add(evalNode);
            }
        }
        //获取权重之后，继续进行
        return  nodeEvalList;
    }

    // 评估对象评价聚合
    public String calObjFuzzy_new(EvalObjIndexModel objconfig){
        if(null == objconfig.getEvalInstance())
            return  null;
        String result = "";
        List<EvalObjEvaluation> objfuzzy = new ArrayList<>();
        EvalNodeEvaluation nodeFuzzy = new EvalNodeEvaluation();
        CopyObjectUtil.copyProperties(objconfig,nodeFuzzy,true);
        List<EvalNodeEvaluation> nodeFuzzyList = nodeEvaluationService.selectByCondition(nodeFuzzy);

        //创建node 与 评价向量的 对应map
        Map<String,List<EvalNodeEvaluation>> nodeFuzzyMap = new HashMap<>();
        for (EvalNodeEvaluation e: nodeFuzzyList){
            if(nodeFuzzyMap.containsKey(e.getNodeCode())){
                nodeFuzzyMap.get(e.getNodeCode()).add(e);
            }else {
                //不存在就创造
                List<EvalNodeEvaluation> el = new ArrayList<>();
                el.add(e);
                nodeFuzzyMap.put(e.getNodeCode(),el);
            }
        }
        // 开始集成至obj表格
        for(Map.Entry<String,List<EvalNodeEvaluation>> entry : nodeFuzzyMap.entrySet()){
            EvalObjEvaluation evalobj = new EvalObjEvaluation();
            evalobj.setEvalInstance(objconfig.getEvalInstance());
            //注意，这里进行了修改，将objcode改成用node的代码带入了，方便后续操作
            evalobj.setEvalObjCode(entry.getKey());
            List<EvalObjEvaluation> objList = objEvalService.selectByCondition(evalobj);
            int size = entry.getValue().size();
//            List<Double> valueList = new ArrayList<>();
//            List<String> ratingList = new ArrayList<>();
            Map<String,Double> ratingValueMap = new HashMap<>();
            for(EvalNodeEvaluation e: entry.getValue()){
//                valueList.add(e.getEvalValue());
//                ratingList.add(e.getRating());
                ratingValueMap.put(e.getRating(),e.getEvalValue());
            }
            //寻找最大值
            Double max = 0.0;
            String rating = "pending";
            for(Map.Entry<String,Double> en:ratingValueMap.entrySet()){
                if(en.getValue()>max) {
                    max = en.getValue();
                    rating = en.getKey();
                }
            }
            evalobj.setEvalValue(max);
            evalobj.setRating(rating);
            int cnt = 0;
            if(objList.size()>0){
                evalobj.setId(objList.get(0).getId());
                cnt = objEvalService.update(evalobj);
                evalobj.setDescription(String.format("update %d recordes", cnt));
                objfuzzy.add(evalobj);
            }else {
                cnt = objEvalService.insert(evalobj);
                evalobj.setDescription(String.format("insert %d recordes", cnt));
                objfuzzy.add(evalobj);
            }

        }
        List<String> riskList = new ArrayList<>();
        //System.out.println(nodeFuzzyList.size());
        for(EvalObjEvaluation item:objfuzzy){
            riskList.add(item.getRating());
        }
        if (riskList.contains("high")){
            result = "风险等级---高";
        }else if(riskList.contains("middle")){
            result = "风险等级---中";
        }else {
            result = "风险等级---低";
        }
        return  result;
        //return  objfuzzy;
    }

    // keyong   459--548可能不能用了
    //建议自己再写三个方法，在接口上换了

    // 2. 根据指标×评级隶属度矩阵,乘指标权重向量,计算节点评级模糊向量，更新eval_node_evaluation表
    public List<EvalNodeEvaluation> calNodeFuzzy(EvalObjIndexModel instance) {
        List<EvalNodeEvaluation> nodeEvalList = new ArrayList<EvalNodeEvaluation>();
        // find all node index fuzzy vector
        EvalNodeIndexEvaluation nodeIndexFuzzy = new EvalNodeIndexEvaluation();
        CopyObjectUtil.copyProperties(instance, nodeIndexFuzzy, true);
        // mult weight vector , get node fuzzy vector
        List<EvalNodeIndexEvaluation> nodeIndexFuzzyList = nodeIndexEvaluationService.selectByCondition(nodeIndexFuzzy);
        // 评估实例的参数,确定指标体系模板
        EvalObjIndexModel indexModel = objIndexModelService.selectByInstance(nodeIndexFuzzy.getEvalInstance());
        // 指标体系模板的参数,获取指标权重(算节点评价向量)
        List<EvalIndexModel> eimList = indexModelService.selectByIndexModel(indexModel.getIndexModel());
        // 制造一个map，以指标为键
        Map<String, EvalIndexModel> indexModelmap = new HashMap<String, EvalIndexModel>();
        for (EvalIndexModel e : eimList) {
            indexModelmap.put(e.getIndexCode(), e);
        }
        // calculate weighted eval value;
        for (EvalNodeIndexEvaluation e : nodeIndexFuzzyList) {
            double w = indexModelmap.get(e.getIndexCode()).getWeight();
            if (StringUtils.isEmpty(String.valueOf(w))) {
                EvalNodeEvaluation nodee = new EvalNodeEvaluation();
                nodee.setDescription(
                        String.format("weight of index %s == %f, %s", e.getIndexCode(), w, String.valueOf(w)));
                nodeEvalList.add(nodee);
                return nodeEvalList;
            }
            e.setEvalValue(e.getEvalValue() * w);
        }
        // System.out.println(nodeIndexFuzzyList.toString());

        // 制造一个map，以节点+评级为键,以指标向量为值（列表）
        Map<String, List<EvalNodeIndexEvaluation>> indexFuzzyMap = new HashMap<String, List<EvalNodeIndexEvaluation>>();
        for (EvalNodeIndexEvaluation e : nodeIndexFuzzyList) {
            if (indexFuzzyMap.containsKey(e.getNodeCode() + e.getRating())) {
                indexFuzzyMap.get(e.getNodeCode() + e.getRating()).add(e);
            } else {
                List<EvalNodeIndexEvaluation> el = new ArrayList<EvalNodeIndexEvaluation>();
                el.add(e);
                indexFuzzyMap.put(e.getNodeCode() + e.getRating(), el);
            }
        }
        // System.out.println(indexFuzzyMap.toString());

        // sum all weighted index eval value
        for (Map.Entry<String, List<EvalNodeIndexEvaluation>> entry : indexFuzzyMap.entrySet()) {
            EvalNodeEvaluation nodeEval = new EvalNodeEvaluation();
            nodeEval.setEvalInstance(nodeIndexFuzzy.getEvalInstance());
            nodeEval.setNodeCode(entry.getValue().get(0).getNodeCode());
            nodeEval.setRating(entry.getValue().get(0).getRating());
            List<EvalNodeEvaluation> nodeList = nodeEvaluationService.selectByCondition(nodeEval);
            
            nodeEval.setEvalValue(0.0);
            for (EvalNodeIndexEvaluation e : entry.getValue()) {
                if (StringUtils.isNotEmpty(String.valueOf(e.getEvalValue())))
                    nodeEval.setEvalValue(nodeEval.getEvalValue() + e.getEvalValue());
                else {
                    nodeEval.setDescription(
                            String.format("index %s eval value == %f", e.getIndexCode(), e.getEvalValue()));
                    nodeEvalList.add(nodeEval);
                    return nodeEvalList;
                }
            }
            nodeEval.setEvalObjCode(entry.getValue().get(0).getEvalObjCode());
            // nodeEval.setIndexModel(nodeIndexFuzzy.getIndexModel());
            int cnt = 0;
            if (nodeList.size() > 0) {
                nodeEval.setId(nodeList.get(0).getId());
                cnt = nodeEvaluationService.update(nodeEval);
                nodeEval.setDescription(String.format("update %d recordes", cnt));
                nodeEvalList.add(nodeEval);
            } else {
                cnt = nodeEvaluationService.insert(nodeEval);
                nodeEval.setDescription(String.format("insert %d recordes", cnt));
                nodeEvalList.add(nodeEval);
            }
        }
        return nodeEvalList;
    }

    //----------------------------------------------------------------------！-----------//
    // 4. 众多节点直接取大或取小，聚合为对象评价向量；
    public List<EvalObjEvaluation> calObjFuzzy(EvalObjIndexModel objconfig) {
        if (null == objconfig.getEvalInstance())
            return null;
        if (null == objconfig.getClusterFunction())
            return null;

        List<EvalObjEvaluation> objfuzzy = new ArrayList<EvalObjEvaluation>();
        ArrayList<String> funcs = new ArrayList<>();
        funcs.add("MAX");
        funcs.add("MIN");
        funcs.add("AVG");
        if (funcs.contains(objconfig.getClusterFunction().toUpperCase())) {
            objfuzzy = objEvalService.selectEvalGroupbyObjRating(objconfig);
        } else {
            EvalObjEvaluation x = new EvalObjEvaluation();
            x.setDescription("cluster function not supported");
            objfuzzy.add(x);
            return objfuzzy; // not supported.
        }

        if (null != objfuzzy) {
            for (EvalObjEvaluation e : objfuzzy) {
                int cnt = 0;
                EvalObjEvaluation query = new EvalObjEvaluation();
                query.setEvalInstance(objconfig.getEvalInstance());
                query.setRating(e.getRating());
                List<EvalObjEvaluation> res = objEvalService.selectByCondition(query);
                if (res.size() > 0) {
                    e.setId(res.get(0).getId());
                    cnt = objEvalService.update(e);
                    e.setDescription(String.format("update %d records", cnt));
                } else {
                    e.setId(null);
                    // System.out.println(e.toString());
                    cnt = objEvalService.insert(e);
                    e.setDescription(String.format("insert %d records", cnt));
                }

            }
        }

        return objfuzzy;
    }



    //--------------------------------------------------------------------------------------！！-----//
    // 3. 众多节点按类别取大计算,聚合为类别评级向量,再聚合为对象评级向量，更新 obj_node_type_evaluation
    public List<NodeTypeEvalVo> calNodeTypeFuzzy(EvalObjIndexModel objconfig) {
        if (null == objconfig.getEvalInstance())
            return null;
        if (null == objconfig.getClusterFunction())
            return null;

        List<NodeTypeEvalVo> typeFuzzy = new ArrayList<NodeTypeEvalVo>();
        // typeFuzzy =
        // nodeIndexEvaluationService.selectEvalGroupbyNodeTypeRating(objconfig);

        typeFuzzy = nodeIndexEvaluationService.evalOnNodeTypeRating(objconfig);

        if (null != typeFuzzy) {
            for (NodeTypeEvalVo e : typeFuzzy) {
                int cnt = 0;
                ObjNodeTypeEvaluation query = new ObjNodeTypeEvaluation();
                query.setEvalInstance(objconfig.getEvalInstance());
                query.setNodeType(e.getNodeType());
                query.setRating(e.getRating());
                List<ObjNodeTypeEvaluation> res = nodeTypeService.selectByCondition(query);
                CopyObjectUtil.copyProperties(e, query, true);
                if (res.size() > 0) {
                    query.setId(res.get(0).getId());
                    cnt = nodeTypeService.update(query);
                    query.setDescription(String.format("update %d records", cnt));
                } else {
                    // System.out.println(e.toString());
                    cnt = nodeTypeService.insert(query);
                    query.setDescription(String.format("insert %d records", cnt));
                }
            }
        }
        return typeFuzzy;
    }

    // join steps above  一建执行
    public EvalResult calInstanceFuzzy(EvalObjIndexModel objconfig) {
        EvalResult eval = new EvalResult();
        eval.setIndexFuzzy(calIndexMembership(objconfig));
//        eval.setNodeFuzzy(calNodeFuzzy(objconfig));
//        eval.setTypeFuzzy(calNodeTypeFuzzy(objconfig)); //这里可能要改
//        eval.setObjFuzzy(calObjFuzzy(objconfig));
        return eval;
    }

    // calculate node tree fuzzy . 没写完
    public List<NodeTypeEvalVo> calNodeTreeFuzzy(EvalObjIndexModel objconfig) {
        // EvalNodeIndexEvaluationVo
        List<EvalNodeIndexEvaluationVo> nodeIndexEvalList = nodeIndexEvaluationService
                .selectNodeIndexesByInstance(objconfig.getEvalInstance());

        List<EvalNodeIndexEvaluationVo> nodeTreeIndexEvalList = nodeIndexEvaluationService
                .getTree(nodeIndexEvalList);

        return null;
    }

}
