// pages/form/form.js
import { request } from "../../request/index.js";
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    bought:[],
    numbers:[],
    shopid:0,
    sum:0,
    phone:'',
    shop:[],
    dis:[],
    phoneFlag:true,
    openid:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onChange(){
    if(/^1[3|4|5|7|8][0-9]{9}$/.test(this.data.phone)){
      this.setData({
        phoneFlag:true
      })
    }else{
      this.setData({
        phoneFlag:false
      })
    }
  },
  onSubmit(){
    if(!this.data.phone){
      wx.showToast({
        title: '请输入手机号',
        icon:'none'
      })
    }else{
      if(this.data.phoneFlag){
        console.log("提交订单") 
          //发起网络请求
        wx.request({
          url: 'http://127.0.0.1:8080/orderForm/getForm',
          data:{
            bought:this.data.bought,
            phone:this.data.phone,
            openId:this.data.openid,
            numbers:this.data.numbers,
            shopId:this.data.shopid+1,
            priceSum:this.data.sum
          }
        });
        
        
        // 1.获取页面栈(返回一个数组,包含了所有曾经去过的页面)
        let pages = getCurrentPages();//可以log看看是什么(里面什么都有--)
        // 2. 拿到上一页(数组长度-2就是上一页)
        let beforePage = pages[pages.length - 2];
        // 3. 执行上一页 onLoad 函数(刷新数据)
        // 假设请求后端数据并渲染页面的函数是: setNumbers()
        beforePage.onLoad();
        // 4. 跳转页面()
        wx.reLaunch({
            // 注意：pages对象中可以获取路径(动态需求时)
            url: '/pages/myorder/myorder',
        });
      }else{
        wx.showToast({
          title: '请输入正确的手机号',
          icon:'none'
        })
      }
    }
  },
  onLoad: function () {
    // console.log(wx.getStorageSync('bought'))
    this.setData({
      sum:wx.getStorageSync('sum'),
      bought:wx.getStorageSync('bought'),
      numbers:wx.getStorageSync('numbers'),
      shop:app.globaldata.shop,
      dis:app.globaldata.distance,
      shopid:app.globaldata.shopid-1,
      openid:app.globaldata.openid
    })
  },

 
})