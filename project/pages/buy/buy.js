// pages/buy/buy.js
import { request } from "../../request/index.js";
const app = getApp();
Page({
  data: {
    //左侧菜单数据
    leftMenuList:[],
    //右侧菜单数据
    rightMenuList:[],
    //被点击的左侧的菜单
    currentIndex:0,
    //设置滑动窗顶部的位置
    toView:"t0",
    //记录所有goods_group高度的数组
    anchorArray:[],
    //记录所有商品购买数量的数组，下标从0开始，对应id-menu_num
    numbers:[],
    //记录购物车价格总数
    sum:0,
    //记录列表数量
    menu_num:0,
    //记录购物车商品数量
    item_num:0,
    //是否打开弹出框，true为打开
    popupFlag:false,
    //已加入购物车的商品数组
    bought:[],
    //是否正在使用弹出框，true为正在使用
    poping_up:false,
    popupMenuFlag:false,
    itemNumberInCart:1,
    item:[],
    //获取屏幕高度
    autoHeight:0,
    shop:[],
    dis:[],
    shopid:0,
    shop_num:1
  },
  //接口返回数据
  Goods:[],
  onLoad: function () {
    wx.clearStorageSync();
    this.getGoods();
     
  },
  //渲染好后计算goods_group组件的高度，存放到anchorArray
  //使用es6特性()=>解决闭包问题，即不能调用this.setData的问题
  //setTimeout()函数延迟代码
  onReady: function(){
    setTimeout(()=>{
      let query = wx.createSelectorQuery().in(this);
            let heightArr =[];
            let h = 0;
            query.selectAll('.goods_group').boundingClientRect((react)=>{
                react.forEach((res)=>{
                    h+=res.height;
                    heightArr.push(h)
                })
                this.setData({
                    anchorArray:heightArr
                });
            }).exec();
    },1000)
  },
  //获取分类数据
  //.then()异步执行，这样就避免了获取不到数据
  getGoods(){
    request({
      url:"http://127.0.0.1:8080/goods/getGoods?shopid="+app.globaldata.shopid
    })
    .then(result=>{
      console.log(result.data.data[0]);
      this.Goods=result.data.data[0].children;
      //构造左侧的大菜单数据
      let leftMenuList=this.Goods.map(v=>v.title);
      //构造右侧的商品数据
      let rightMenuList=this.Goods;
      let currentIndex=0;
      let autoHeight = wx.getSystemInfoSync().windowHeight    // 获取当前窗口的高度
      let numbers =[];
      let id=[];
      let n =result.data.data[0].goods_num;
      let menu_num=result.data.data[0].menu_num;
      for(let i=0;i<n;i++){
        numbers.push(0);
        id.push(0);
      }
      // let numbers=this.Goods.map(v=>v.number);
      this.setData({
        leftMenuList,
        rightMenuList,
        currentIndex,
        numbers:numbers,
        menu_num:menu_num,
        autoHeight:autoHeight,
        shop:app.globaldata.shop,
        dis:app.globaldata.distance,
        shopid:app.globaldata.shopid-1

        // numbers
      });
      // this.setNumbers();
    })
  },
  //设置numbers初值
  // setNumbers(){
  //     let numbers =[];
  //     let n =this.Goods[0].goods_num;
  //     let menu_num=this.Goods[0].menu_num;
  //     for(let i=0;i<n;i++){
  //       numbers.push(0);
  //     }
  //     let autoHeight = wx.getSystemInfoSync().windowHeight    // 获取当前窗口的高度
  //     this.setData({
  //       numbers:numbers,
  //       menu_num:menu_num,
  //       autoHeight:autoHeight
  //     });
  //     this.setData({
  //       shop:app.globaldata.shop,
  //       dis:app.globaldata.distance,
  //       shopid:app.globaldata.shopid-1
  //     }) 
  //     // const numbers = wx.getStorageSync('numbers');
  // },
  //左侧菜单的点击事件,每次点击左侧菜单后会移动右侧商品列表，这会触发handelScroll()
  handleItemTap(e){
    /*1.获取被点击的标题身上的索引
      2.给data中的currentIndex赋值就可以了
      const {index}=e.currentTarget.dataset等价于
      const index=e.currentTarget.dataset.index */
      const {index}=e.currentTarget.dataset;
      
      // let rightContent = this.Goods;
      this.setData({
        currentIndex:index,
        toView:"t"+index
      })
      
  },
  //scroll-view滚动时触发
  handelScroll(e) {
    // console.log(123)
    let scrollTop=e.detail.scrollTop;
    let scrollArr= this.data.anchorArray;
    if(scrollTop>=scrollArr[scrollArr.length-1]-this.data.autoHeight){
            return;
      }else {
          for(let i=0;i<scrollArr.length;i++){
                if(scrollTop>=0&&scrollTop<scrollArr[0]){
                    this.setData({
                        currentIndex: 0
                    });
                }else if(scrollTop>=scrollArr[i-1]&&scrollTop<scrollArr[i]) {
                    this.setData({
                        currentIndex: i
                    });
             }
         }
     }
},
//button的加入购物车函数
  addCart(e){
    const {price}=e.currentTarget.dataset;
    let index = "numbers[" +e.currentTarget.dataset.index+"]";
    
    //判断购物车内是否有物品，真值为有
    let flag = true;
    //加入bought数组
    if(this.data.bought.length==0){
      this.data.bought.push(e.currentTarget.dataset.item);
    }else{
      this.data.bought.forEach(i=>{
        //若购物车内有相同的物品，则不加入购物车，使flag为false
        if(i.id-this.data.menu_num==e.currentTarget.dataset.index){
          flag = false; 
        }
      });
      //购物车内没有物品，此时应该添加进去
      if(flag) this.data.bought.push(e.currentTarget.dataset.item);
    }
     //需要刷新一下appData，可以看出通过push()方法操作数组后并不会通知appData
    //也可以看出，this.setData()方法在修改Data域的同时，也会通知appData刷新数据
    this.setData({
      sum:this.data.sum+price,
      [index]:this.data.numbers[e.currentTarget.dataset.index]+1,
      item_num:this.data.item_num+1,
      bought:this.data.bought
    });
    
  },
//stepper步进器的加号点击事件
  plus(e){
    this.addCart(e);
  },
//stepper步进器的减号点击事件
  minus(e){
    const {price}=e.currentTarget.dataset;
    let index = "numbers[" +e.currentTarget.dataset.index+"]";
    //将numbers中为0的item从bought数组中删除
    
    this.setData({
      sum:this.data.sum-price,
      [index]:this.data.numbers[e.currentTarget.dataset.index]-1,
      item_num:this.data.item_num-1
    });
    this.data.bought.forEach(i=>{
      
      if(this.data.numbers[i.id-this.data.menu_num]==0){
        //找bought数组中json对象的id属性值为4的对象在数组中的下标位置
        let index = this.data.bought.findIndex((item)=>item.id===i.id);
        //删除元素
        this.data.bought.splice(index,1);
        //需要刷新一下appData，可以看出通过splice()方法操作数组后并不会通知appData
        //也可以看出，this.setData()方法在修改Data域的同时，也会通知appData刷新数据
        this.setData({
          bought:this.data.bought
        });
      }
    });
    if(this.data.numbers.indexOf(1)==-1){
      this.setData({
        popupFlag:false
      })
    }
  },
//点击购物车展示底部弹出框
  show(){
    if(this.data.item_num==0){
      wx.showToast({
        title: '购物车是空的哦π_π',
        icon:'none'
      })
    }else{
      if(!this.data.poping_up){
        this.setData({
          popupFlag:true,
          poping_up:true
        })
      }else{
        this.setData({
          popupFlag:false,
          poping_up:false
        })
      }
    }
  },
//点击遮罩关闭弹出框事件
  closePopup(){
    this.setData({
      popupFlag:false
    })
  },
//清空购物车
  clearCart(){
    this.onLoad();
    this.setData({
      bought:[],
      popupFlag:false,
      poping_up:false,
      sum:0,
      item_num:0
    });
  },
  showMenu(result){
    console.log(result.currentTarget.dataset.item);
    this.setData({
      item:result.currentTarget.dataset.item,
      popupMenuFlag:true
    })
  },
  onClose(){
    this.setData({
      popupMenuFlag:false
    })
  },
  onChange(e){
    this.setData({
      itemNumberInCart:e.detail
    })
  },
  detailsAdd(e){
    const {price}=e.currentTarget.dataset;
    let index = "numbers[" +e.currentTarget.dataset.index+"]";
    console.log(this.data.itemNumberInCart)
    //判断购物车内是否有物品，真值为有
    let flag = true;
    //加入bought数组
    if(this.data.bought.length==0){
      this.data.bought.push(e.currentTarget.dataset.item);
    }else{
      this.data.bought.forEach(i=>{
        //若购物车内有相同的物品，则不加入购物车，使flag为false
        if(i.id-this.data.menu_num==e.currentTarget.dataset.index){
          flag = false; 
        }
      });
      //购物车内没有物品，此时应该添加进去
      if(flag) this.data.bought.push(e.currentTarget.dataset.item);
    };
     //需要刷新一下appData，可以看出通过push()方法操作数组后并不会通知appData
    //也可以看出，this.setData()方法在修改Data域的同时，也会通知appData刷新数据
    this.setData({
      popupMenuFlag:false,
      sum:this.data.sum+price*this.data.itemNumberInCart,
      [index]:this.data.numbers[e.currentTarget.dataset.index]+this.data.itemNumberInCart,
      item_num:this.data.item_num+this.data.itemNumberInCart,
      bought:this.data.bought,
      itemNumberInCart:1
    })
    setTimeout(this.setNewNum,500);
  },
  setNewNum(){
    this.setData({
      shop_num:1
    })
  },
  // scroll-view到底部时触发
  // handelscrolltolower(){
  //   this.setData({
  //     currentIndex: this.data.rightMenuList.length-1,
  //     tolowerFlag:true
  // });
  // }
  navigate(){
    wx.navigateTo({
      url: '/pages/map/map',
    })
  },
  nav(){
    wx.setStorageSync('bought', this.data.bought);
    wx.setStorageSync('numbers', this.data.numbers);
    wx.setStorageSync('sum', this.data.sum);
    wx.navigateTo({
      url: '/pages/form/form',
    })
  }
  // handleGetUserInfo(e) {
  //   console.log(e.detail.userInfo);
  //   wx.login({
  //     success (res) {
  //       if (res.code) {
  //         //发起网络请求
  //         console.log(res.code);
  //       } else {
  //         console.log('登录失败！' + res.errMsg)
  //       }
  //     }
  //   })
  // }
})