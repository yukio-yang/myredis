package com.yukio.abc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yukio.abc.entity.Account;
import com.yukio.abc.entity.vo.AccountVo;
import com.yukio.common.service.IService;

import java.util.ArrayList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yukio
 * @since 2022-02-08
 */
public interface IAccountService extends IService<Account> {
	ArrayList<Account> selectList(Integer start, Integer end, Account account);

	IPage select1(AccountVo account);
}
