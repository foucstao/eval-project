package com.eval.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSON;
import com.eval.pojo.EvalNode;
import com.eval.pojo.EvalNodeIndexEvaluation;
import com.eval.pojo.EvalObjIndexModel;
import com.eval.pojo.EvalObjNodeIndexInput;
import com.eval.pojo.EvalObjNodeLink;
import com.eval.pojo.EvalObject;
import com.eval.vo.DikarNodeIndex;
import com.eval.vo.InputVo;
import com.eval.vo.InstanceVo;
import com.eval.vo.NodeVo;

@Service
public class InputProcess {

    @Autowired
    EvalObjectService eService;

    @Autowired
    EvalObjIndexModelService objIndexModelService;

    @Autowired
    EvalNodeService nodeService;

    @Autowired
    EvalObjNodeLinkService objNodeLinkService;

    @Autowired
    EvalObjNodeIndexInputService objNodeIndexLinkService;

    @Autowired
    EvalNodeIndexEvaluationService nodexIndexEvaluationService;

    // read file with json format
    public String readJsonFile(String path) {
        String jsonStr = "";
        try {
            File jsonFile = new File(path);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
    // 一、全局配置 （一次配置，前端页面配置）
    // 1. 确定生产要素类别
    // 2. 创建指标库
    // 3. 配置全部指标的隶属度函数参数
    // 4. 根据生产要素配置指标模板，配置每个指标的隶属度函数
    // 5. 对指标库打分，创建重要性矩阵
    // 6. 根据指标模板，计算指标权重；
    // 7. 确定评级表

    // 二、创建节点库. 输入全部节点，以及节点上下级，所属要素类别，(每个企业配置一次)
    // 根据节点标识，是否挂接评价对象；update eval-node节点库
    // and obj-node-link table，初始化评价对象节点表；（判空）
    // node.json
    public List<NodeVo> createNodeTable(List<NodeVo> list) {
        // List<NodeVo> list = new ArrayList<NodeVo>();
        // list = JSON.parseArray(nodeJson, NodeVo.class);
        // System.out.println(list.toString());
        // "code": "worker2mac1",
        // "pcode": "workermachine",
        // "name": "工人2-机器1",
        // "source": "worker2",
        // "sink": "mac1",
        // "nodeType": "WORKER-MACHINE",
        // "isLeaf": 1,
        // "relationType": "relation",
        // "description": null,
        // "obj": "workshop",
        // "institution": "XCOPNATION"
        // insert or update node table
        boolean hasUpdatePcode = false;
        for (NodeVo nv : list) {
            if (null == nv.getCode() || nv.getCode().trim().isEmpty())
                continue;
            if (null == nv.getInstitution() || nv.getInstitution().trim().isEmpty())
                continue;

            // set query param
            EvalNode n = new EvalNode();
            n.setCode(nv.getCode());
            n.setInstitution(nv.getInstitution());
            List<EvalNode> nl = nodeService.selectNodesByObj(n);
            // after
            CopyObjectUtil.copyProperties(nv, n, true);
            // n.setCode(nv.getCode());
            // n.setPcode(nv.getPcode());
            // n.setName(nv.getName());
            // n.setSource(nv.getSource());
            // n.setSink(nv.getSink());
            // n.setNodeType(nv.getNodeType());
            // n.setIsLeaf(nv.getIsLeaf());
            // n.setRelationType(nv.getRelationType());
            // n.setDescription(nv.getDescription());
            // n.setInstitution(nv.getInstitution());
            if (null != n.getPcode() && !n.getPcode().trim().isEmpty()) {
                hasUpdatePcode = true;
            }
            String des = "";
            if (nl.size() > 0) {
                if ((null == n.getPcode() || n.getPcode().trim().isEmpty())
                        && (null == n.getName() || n.getName().trim().isEmpty())
                        && (null == n.getSource() || n.getSource().trim().isEmpty())
                        && (null == n.getSink() || n.getSink().trim().isEmpty())
                        && (null == n.getNodeType() || n.getNodeType().trim().isEmpty())
                        && (null == n.getIsLeaf())
                        && (null == n.getRelationType() || n.getRelationType().trim().isEmpty())) {

                } else {
                    n.setId(nl.get(0).getId());
                    int cnt = nodeService.update(n);
                    des += (String.format("EvalNode update %d.", cnt));

                }

            } else {
                int cnt = nodeService.insert(n);
                des += (String.format("EvalNode insert %d.", cnt));
            }
            nv.setRemark(des);

        }
        // udpate pid;// pid cannot gotten above.
        if (hasUpdatePcode) {
            int nt = nodeService.updatePidByInstitution(list.get(0).getInstitution());
            NodeVo r = new NodeVo();
            r.setRemark(String.format("EvalNode update %d pids.", nt));
            list.add(r);
        }

        // udpate obj-node-link table
        for (NodeVo nv : list) {
            if (null == nv.getEvalObjCode() || nv.getEvalObjCode().trim().isEmpty())
                continue;
            if (null == nv.getCode() || nv.getCode().trim().isEmpty())
                continue;
            if (null == nv.getInstitution() || nv.getInstitution().trim().isEmpty())
                continue;

            EvalObjNodeLink objnode = new EvalObjNodeLink();
            objnode.setNodeCode(nv.getCode());
            objnode.setInstitution(nv.getInstitution());

            List<EvalObjNodeLink> onl = objNodeLinkService.selectByCondition(objnode);

            objnode.setEvalObjCode(nv.getEvalObjCode());
            String des = "";
            if (onl.size() > 0) {
                // if eval-obj'nodes have input values, cannot update.
                EvalObjNodeIndexInput q = new EvalObjNodeIndexInput();
                q.setEvalObjCode(nv.getEvalObjCode());
                q.setNodeCode(nv.getCode());
                List<EvalObjNodeIndexInput> re = objNodeIndexLinkService.selectByCondition(q);
                if (re.size() > 0) {
                    des += (String.format("obj %s cannot be updated.", nv.getEvalObjCode()));
                } else {
                    objnode.setId(onl.get(0).getId());
                    int cnt = objNodeLinkService.update(objnode);
                    des += (String.format("EvalObjNodeLink update %d.", cnt));
                }
            } else {
                int cnt = objNodeLinkService.insert(objnode);
                des += (String.format("EvalObjNodeLink insert %d.", cnt));
            }
            nv.setRemark(nv.getRemark() + des);
        }

        return list;

    }

    // 三、评价实例原型配置 （每个企业配置一次，定期更新，JSON格式初始化接口）
    // 1. 创建评价对象，（判空）
    // 2. 确定一个评价实例原型，选择指标模板，重要性矩阵版本，评级表版本，聚集函数类型
    // instance.json
    public InstanceVo createEvalObjAndInstance(InstanceVo instanceVo) {
        // JSONObject jsonObject = JSONObject.parseObject(instanceJson);
        // InstanceVo instanceVo = JSON.parseObject(instanceJson, InstanceVo.class);
        // System.out.println(instanceVo.toString());
        // update obj and instance table "evalObjName" : "xx生产车间",
        // insert obj table if obj not exist
        if (null == instanceVo.getEvalObjCode() || instanceVo.getEvalObjCode().trim().isEmpty()) {
            instanceVo.setRemark(String.format("getEvalObjCode is %s.", instanceVo.getEvalObjCode()));
            return instanceVo;
        }
        if (null == instanceVo.getInstitution() || instanceVo.getInstitution().trim().isEmpty()) {
            instanceVo.setRemark(String.format("getInstitution is %s.", instanceVo.getInstitution()));
            return instanceVo;
        }
        if (null == instanceVo.getEvalInstance() || instanceVo.getEvalInstance().trim().isEmpty()) {
            instanceVo.setRemark(String.format("getEvalInstance is %s.", instanceVo.getEvalInstance()));
            return instanceVo;
        }
        if (null == instanceVo.getIndexModel() || instanceVo.getIndexModel().trim().isEmpty()) {
            instanceVo.setRemark(String.format("getIndexModel is %s.", instanceVo.getIndexModel()));
            return instanceVo;
        }
        if (null == instanceVo.getImportanceVersion() || instanceVo.getImportanceVersion().trim().isEmpty()) {
            instanceVo.setRemark(String.format("getImportanceVersion is %s.", instanceVo.getImportanceVersion()));
            return instanceVo;
        }
        if (null == instanceVo.getClusterFunction() || instanceVo.getClusterFunction().trim().isEmpty()) {
            instanceVo.setRemark(String.format("getClusterFunction is %s.", instanceVo.getClusterFunction()));
            return instanceVo;
        }
        if (null == instanceVo.getRateVersion() || instanceVo.getRateVersion().trim().isEmpty()) {
            instanceVo.setRemark(String.format("getRateVersion is %s.", instanceVo.getRateVersion()));
            return instanceVo;
        }

        // update or insert eval-obj table

        EvalObject eo = new EvalObject();
        eo.setEvalCode(instanceVo.getEvalObjCode());
        eo.setInstitution(instanceVo.getInstitution());
        List<EvalObject> list = eService.selectByCode(eo.getEvalCode());
        eo.setEvalObj(instanceVo.getEvalObjName());
        String des = "";
        if (list.size() > 0) {
            if (null == instanceVo.getEvalObjName() || instanceVo.getEvalObjName().trim().isEmpty()) {
                instanceVo.setRemark(String.format("getEvalObjName is %s.", instanceVo.getEvalObjName()));
            } else {
                EvalObject eo1 = new EvalObject();
                eo1.setId(list.get(0).getId());
                eo1.setEvalObj(instanceVo.getEvalObjName());
                int cnt = eService.update(eo1);
                des += (String.format("EvalObject update %d.", cnt));
            }

        } else {
            int cnt = eService.insert(eo);
            des += (String.format("EvalObject insert %d.", cnt));
        }

        // update or insert eval-obj-index-model instance table
        EvalObjIndexModel eoim = new EvalObjIndexModel();
        CopyObjectUtil.copyProperties(instanceVo, eoim, true);
        // eoim.setEvalObjCode(instanceVo.getEvalObjCode());
        // eoim.setEvalInstance(instanceVo.getEvalInstance());
        // eoim.setClusterFunction(instanceVo.getClusterFunction());
        // eoim.setImportanceVersion(instanceVo.getImportanceVersion());
        // eoim.setIndexModel(instanceVo.getIndexModel());
        // eoim.setRateVersion(instanceVo.getRateVersion());
        // eoim.setInstitution(instanceVo.getInstitution());

        // can instance be modified?
        EvalObjIndexModel objind = objIndexModelService.selectByInstance(eoim.getEvalInstance());
        if (null == objind) {
            int cnt = objIndexModelService.insert(eoim);
            des += (String.format("EvalObjIndexModel insert %d.", cnt));
        } else {
            // if instance is inserted into obj-node-index-value table, then it cannot be
            // modified.
            List<EvalObjNodeIndexInput> r = objNodeIndexLinkService.selectByInstance(eoim.getEvalInstance());
            if (r.size() == 0) {
                eoim.setId(objind.getId());
                int cnt = objIndexModelService.update(eoim);
                des += (String.format("EvalObjIndexModel update %d.", cnt));
            } else {
                instanceVo.setRemark(
                        instanceVo.getRemark() + "input values of this instance exist, its config cannot be modified.");
            }

        }

        instanceVo.setDescription(des);
        return instanceVo;

    }

    public InstanceVo createEvalInstanceAndIndexTemplate(InstanceVo instanceVo) {
        InstanceVo inst = createEvalObjAndInstance(instanceVo);
        // 3. 根据评价实例初始化实例节点指标模板，
        List<DikarNodeIndex> indexModel = objNodeIndexLinkService.generateNodeIndexTemplate(inst.getEvalInstance());
        inst.setDikar(indexModel);
        return inst;
    }

    // 四、原始数值输入处理 （根据数据更新频率接收处理，JSON格式批量接口）
    // 2. 根据inputvalue json 更新全部指标的值；
    // create eval-node-index-input-value table
    // inputvalue.json
    public List<InputVo> updateInputTable(List<InputVo> list) {
        // List<InputVo> list = new ArrayList<InputVo>();
        // list = JSON.parseArray(inputJson, InputVo.class);
        // System.out.println(list.toString());

        for (InputVo input : list) {
            EvalObjNodeIndexInput index = new EvalObjNodeIndexInput();
            index.setEvalInstance(input.getEvalInstance());
            index.setEvalObjCode(input.getEvalObjCode());
            index.setNodeCode(input.getNodeCode());
            index.setIndexCode(input.getIndexCode());

            List<EvalObjNodeIndexInput> r = objNodeIndexLinkService.selectByCondition(index);
            if (r.size() > 0) {
                // assert if evaluated.
                EvalNodeIndexEvaluation eval = new EvalNodeIndexEvaluation();
                CopyObjectUtil.copyProperties(index, eval, true);
                List<EvalNodeIndexEvaluation> r1 = nodexIndexEvaluationService.selectByCondition(eval);
                if (r1.size() > 0) {
                    // cannot modified, please create a new instance.
                    input.setRemark(String.format("evaled."));
                } else {
                    EvalObjNodeIndexInput it = new EvalObjNodeIndexInput();
                    it.setId(r.get(0).getId());
                    it.setInputValue(input.getInputValue());
                    int u = objNodeIndexLinkService.update(it);
                    input.setRemark(String.format("update %d.", u));
                }

            } else {
                input.setRemark(String.format("no template."));
            }

        }
        return list;
    }

    // 五、评价实例模型计算
    // 见接口
}
