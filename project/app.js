//app.js
var QQMapWX = require('/libs/qqmap-wx-jssdk.js');
import { request } from "/request/index.js";
var qqmapsdk;
App({
    //全局变量
    globaldata:{
        
        index:-1,
        shortestDistance:999999,
        distance:[],
        shop :[],
        mylatitude:0,
        mylongitude:0,
        shoplatitude:[],
        shoplongitude:[],
        shopid:1,
        appid:'wxdfaf253193f2837d',
        secret:'bd556b826e8f8a83cfa461422c885101',
        openod:''
    },
    //加载时触发
    onLaunch(){
        
        // 实例化API核心类
        qqmapsdk = new QQMapWX({
            key:'BH5BZ-2LGW2-WP6UU-CIJ5L-XL727-T5BUM'
        });
        request({
            url:"http://127.0.0.1:8080/shop/getAddress"
        })
        .then(result=>{
            let shop = result.data.data;
            this.globaldata.shop = shop;
            this.getLocation();
        });
    },
    //获取当前位置信息，需要获取权限
    getLocation(){
        wx.getLocation({
          type:"gcj02",
          success:((res)=>{
            this.globaldata.mylatitude = res.latitude;
            this.globaldata.mylongitude = res.longitude;
            this.getShopDistance();
          })
        });
    },
    //获得所有商店的距离
    getShopDistance(){
        let str="";
        let length = this.globaldata.shop.length;
        for(let i = 0;i<length;i++){
            qqmapsdk.geocoder({
                address:this.globaldata.shop[i].address,
                success:((res)=>{
                  str = res.result.location.lat+","+res.result.location.lng
                  qqmapsdk.calculateDistance({
                    from:this.globaldata.mylatitude+","+this.globaldata.mylongitude,
                    to:str,
                    success:((re)=>{
                        this.globaldata.distance.push(re.result.elements[0].distance/1000);
                        this.globaldata.shoplongitude.push(res.result.location.lng);
                        this.globaldata.shoplatitude.push(res.result.location.lat);
                        if(i == length-1){
                            
                            for(let j = 0;j<this.globaldata.distance.length;j++){
                                if(this.globaldata.shortestDistance>this.globaldata.distance[i]){
                                    this.globaldata.shortestDistance = this.globaldata.distance[i];
                                    this.globaldata.index = j;
                                    this.globaldata.shopid = j+1;
                                }
                            }
                        }
                    })
                  });
                }),
                fail: function (res) {
                    console.log(res);
                }
            }); 
        }    
    },
    //获得距离当前最近的商店
    // getClosestShop(dis){
    //     console.log(this.globaldata.shortestDistance>dis)
    //      if(this.globaldata.shortestDistance>dis){   
    //         this.globaldata.shortestDistance = dis;
    //         this.globaldata.index += 1;
    //         this.globaldata.shopid = this.globaldata.index+1;
    //         return true;
    //      }
    // },
    
})