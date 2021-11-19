package com.gdpu.common;

import java.util.ArrayList;
import java.util.List;

public class TreeNodeBuilder {

    /*
    * 把没有层级关系的集合变成有层级关系的集合
    * @param treeNodes 没有分层的树列表
    * @param topId     上一层结点的id
    * @return
    *
    * */
    //将列表内的树结点构建成树，规则是pid=topId的为父层，id=pid为子层
    public static List<TreeNode> build(List<TreeNode> treeNodes, Integer topId){
        //pid也就是子结点的父结点id，我们用根结点的id来索引子结点的pid，相等就把他加入到子树列表里面
        //这是一棵用列表来保存的树，根节点pid=0;第二层pid=1，用列表保存,第三层pid与第二层列表内的id相同，用列表保存。
        List<TreeNode> nodes = new ArrayList<>();
        for (TreeNode n1: treeNodes) {    //寻找pid为1的结点，我们认为pid=1是树的根节点
            if(n1.getPid() == topId){
                nodes.add(n1);
            }
            for (TreeNode n2:treeNodes) {    //寻找pid=this.id的子结点，将他加入到树的列表中
                if(n1.getId() == n2.getPid()){
                    n1.getChildren().add(n2);
                }
            }
        }
        return nodes;
    }
}
