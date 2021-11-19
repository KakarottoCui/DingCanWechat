package com.gdpu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdpu.bean.Leftmenu;
import com.gdpu.bean.Admin;
import com.gdpu.common.*;
import com.gdpu.service.LeftmenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@RestController
@RequestMapping("/leftmenu")
public class LeftmenuController {
    @Resource
    private LeftmenuService leftmenuService;

    //动态生成左边导航菜单栏，通过生成Json传送到前端页面实现
    @RequestMapping("loadIndexLeftMenuJson")
    public DataGridView loadIndexLeftMenuJson(){
        //查询所有菜单
        QueryWrapper<Leftmenu> queryWrapper = new QueryWrapper<>();
        List<Leftmenu> list = null;
        //获得用户  判断用户的类型
        Admin Admin = (Admin) WebUtils.getSession().getAttribute("user");
        if(Admin.getRoleId() == Constast.USER_TYPE_SUPER){ //超级管理员拥有所有菜单
            list = leftmenuService.list(queryWrapper);
        }else {                                             //普通仓管没有系统管理
            //根据用户ID+角色+权限去查询
            queryWrapper.notIn("id",Constast.SUPER_USER_OWN);       //反选id为4的列，要让普通仓管没有系统管理的menu
            list = leftmenuService.list(queryWrapper);
        }
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Leftmenu leftmenu: list) {
            Integer id = leftmenu.getId();
            Integer pid = leftmenu.getPid();
            String title = leftmenu.getTitle();
            String icon = leftmenu.getIcon();
            String href = leftmenu.getHref();
            Boolean spread = leftmenu.getOpen()==Constast.OPEN_TRUE?true:false;
            treeNodes.add(new TreeNode(id,pid,title,icon,href,spread));
        }
        //用获得的菜单信息生成相应的Json
        List<TreeNode> list1 = TreeNodeBuilder.build(treeNodes,1);
        return new DataGridView(list1);
    }
}

