package com.yukio.abc.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yukio.abc.entity.Account;
import com.yukio.abc.entity.vo.AccountVo;
import com.yukio.abc.mapper.AccountMapper;
import com.yukio.abc.service.IAccountService;
import com.yukio.common.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yukio
 * @since 2022-02-08
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

	@Autowired
	public AccountMapper accountMapper;

	@Override
	public ArrayList<Account> selectList(Integer start, Integer end, Account account) {
		start = (start - 1) * end;
		return accountMapper.selectList(start, end, account);
	}

	@Override
	public IPage select1(AccountVo account) {
		return lambdaQuery().like(StrUtil.isNotEmpty(account.getName()), Account::getName, account.getName())
				.eq(account.getBalance() > 0, Account::getBalance, account.getBalance())
				.page(new Page<>(account.getPageNum(), account.getPageSize()));
	}
}
