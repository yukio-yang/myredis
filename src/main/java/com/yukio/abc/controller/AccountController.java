package com.yukio.abc.controller;


import com.yukio.abc.entity.Account;
import com.yukio.abc.entity.vo.AccountVo;
import com.yukio.abc.service.IAccountService;
import com.yukio.common.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yukio
 * @since 2022-02-08
 */
@Api(value = "账户类接口")
@Controller
@RequestMapping("/abc/account")
public class AccountController {
	@Autowired
	public IAccountService iAccountService;

	@ApiOperation(value = "账户分页查询22", notes = "传入条件是账户对象22")
	@PostMapping("/selectPage")
	@ResponseBody
	public R select1(@ApiParam(value="账户分页对象")@RequestBody AccountVo account) {
		return R.ok("请求成功", iAccountService.select1(account));
	}

	@ApiOperation(value = "账户分页查询", notes = "传入条件是账户对象")
	@PostMapping("/selectPage2")
	@ResponseBody
	public R select2(@ApiParam(value="账户对象") @RequestBody Account account) {
		return R.ok("请求成功", iAccountService.selectList(1,2,account));
	}
}
