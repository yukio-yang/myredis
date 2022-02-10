package com.yukio.abc.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BaseVO {
    @NotNull
    private Long pageSize = 10L;
    @NotNull
    private Long pageNum = 1L;
//    @Past
//    private Date startTime;
//    @Past
//    private Date endTime;
//
//    private List<Long> ids;
}