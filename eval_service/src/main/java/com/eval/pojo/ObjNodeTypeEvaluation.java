package com.eval.pojo;

import com.base.common.BaseEntity;

import lombok.Data;

@Data
public class ObjNodeTypeEvaluation extends BaseEntity {
    // CREATE TABLE `obj_node_type_evaluation` (
    // `id` int NOT NULL AUTO_INCREMENT,
    // `eval_instance` varchar(45) DEFAULT NULL,
    // `eval_obj_code` varchar(100) NOT NULL,
    // `node_type` varchar(100) NOT NULL,
    // `rating` varchar(50) DEFAULT NULL,
    // `eval_value` double DEFAULT NULL,
    // `description` varchar(100) DEFAULT NULL,
    // PRIMARY KEY (`id`)
    // ) ENGINE=InnoDB DEFAULT CHARSET=utf8
    private Long id;
    private String evalInstance;
    private String evalObjCode;
    private String nodeType;
    private String rating;
    private Double evalValue;
    private String description;

}
