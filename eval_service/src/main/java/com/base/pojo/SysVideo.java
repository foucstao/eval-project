package com.base.pojo;

import com.base.common.BaseEntity;

import lombok.Data;

import java.util.Date;

@Data
public class SysVideo extends BaseEntity {

		/** 主键*/
     private Long id;
     
		/** 摄像头名称*/
     private String name;
     
		/** 播放地址*/
     private String url;
     
		/** 位置*/
     private String address;
     
		/** 备注*/
     private String remark;
     
		/** 创建时间*/
     private Date createTime;

    /** 视频类型*/
     private String type;
     
}