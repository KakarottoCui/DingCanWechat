// pages/mine/mine.js

import { request } from "../../request/index.js";
const app = getApp();
Page({
  data: {
    order:[]
  },
  onLoad: function () {
    request({
      url:"http://127.0.0.1:8080/orderForm/loadOrderForm"
    })
    .then(result=>{
      console.log(result.data.data);
      //逆序
      result.data.data.reverse();
      this.setData({
        order:result.data.data
      })
    });
  },
  
 
})