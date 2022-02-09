package com.yukio.abc.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author yukio
 * @create 2022-02-08 16:18
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class AccountVo extends BaseVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private Double balance;
}
