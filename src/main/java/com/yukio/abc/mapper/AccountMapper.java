package com.yukio.abc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yukio.abc.entity.Account;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yukio
 * @since 2022-02-08
 */
public interface AccountMapper extends BaseMapper<Account> {
	ArrayList<Account> selectList(@Param("start") Integer start, @Param("end")Integer end,  @Param("account")Account account);
}
