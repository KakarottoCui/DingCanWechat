


export const request=(params)=>{

  //显示加载中效果
  wx.showLoading({
    title: '加载中',
    mask:true
  });

  return new Promise((resolve,reject)=>{
    wx.request({
      ...params,
      success:(result)=>{
        resolve(result);
      },
      fail:(err)=>{
        reject(err);
      },
      complete:()=>{
        //关闭正在等待的图标
        wx.hideLoading();
      }
    });
  })
}