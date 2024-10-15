package com.eval.pojo;

import lombok.Data;

@Data
public class IndexMembership {
    // index_membership
    // `id` int NOT NULL AUTO_INCREMENT,
    // `index_code` varchar(100) NOT NULL,
    // `rating_code` varchar(100) NOT NULL,
    // `affiliation` varchar(100) NOT NULL COMMENT 'three types of function: gauss,
    // triangle, trapezoid',
    // `param1` double DEFAULT NULL COMMENT ' param1 refer to the first paramter of
    // the affiliation function',
    // `param2` double DEFAULT NULL,
    // `param3` double DEFAULT NULL,
    // `param4` double DEFAULT NULL,
    // `param5` double DEFAULT NULL,
    // `version` varchar(20) DEFAULT NULL,

    private Long id;

    private String indexCode;

    private String ratingCode;

    private String membershipFunction;

    private Double param1;

    private Double param2;

    private Double param3;

    private Double param4;

    private Double param5;

    private String version;

}
