package com.eval.pojo;

import lombok.Data;

@Data
public class NodeTypeIndexLink {
    // CREATE TABLE `nodetype_index_link` (
    // `id` int NOT NULL AUTO_INCREMENT,
    // `node_type_code` varchar(100) NOT NULL,
    // `index_code` varchar(100) DEFAULT NULL,
    // `version` varchar(45) NOT NULL DEFAULT 'v1',
    // PRIMARY KEY (`id`)
    // )
    private Long id;

    private String nodeTypeCode; // MACHINE ...

    private String indexCode;

    private String version;
}
