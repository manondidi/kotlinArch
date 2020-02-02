# kotlinArch[![](https://jitpack.io/v/manondidi/kotlinArch.svg)](https://jitpack.io/#manondidi/kotlinArch)
kotlin开发脚手架

>首先这是个一个mvp框架
>我帮你做好了mvp的基类
>并做好相关的模板, 一键生成mvp 三个类 activity fragment各一套
>并且 我封装了一个coverframelayout 作为一个framelayout 为所有view的root节点
>我在内部写好了三个view 并处理好他们的逻辑关系 emptyview errorview loadingview
>并且 这些coverview 的样式 支持三种配置方式  
>application中统一配置
>用同名xml覆盖 我的默认xml名 全局替换
>在oncreate的时候 手动传入 coverframe.xxxview 的view
>并且 你也可以使用我的mvp框架, 而不在root节点嵌套coverframelayout,因为我是用了kotlin的 ?? 操作,预先做了判空,也不会崩溃 逻辑照样走,并且用uber的 >autodisposed框架作为生命周期观察,方便使用rxjava rxkotlin时destory时取消订阅
>另外我还做了一个 关于 recyclerView的activity 和fragment 分别继承 上面的基类,
>除了具有上述的功能外,还具有 几个功能 recyclerView item解耦, 只要注册好 类和item的关系,然后传入对应的model对象,就会自动展示,不需要维护item的类别, >还有下拉刷新套件 ,也可以在application中统一配置或者在具体页面中单独配置并且在presenter中做好他们的生命周期,还有分页策略
>分页是基于策略模式的, 我把分页规则的计算抽象成策略, 传入不同的策略对象 会有不同的计算方式
>目前支持 pagesize  和offsetid两种,开放封闭原则
>并且做了一个模板 一键生成 对应的itembinder


开发模板,可以一键生成mvp类,并自动写好相应通用代码 :https://github.com/manondidi/kotlinArchTemplate


### 混淆请参考demo工程的配法

安装

```

allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}

```
```

implementation 'com.github.manondidi:kotlinArch:latest-version'
```



##Todo
- [x] rxkotlin

- [x] rxbus

- [x] 页面destory rx自动取消

- [x] mvp

- [ ] ~~databinding或viewmodel~~(业务层自己实现)

- [x] 策略模式的自动分页

- [x] 各种cover封装

- [x] 列表数据的解耦 itembinder

- [x] mock管理

- [x] 开发模板(自动生成代码)

- [x] 详细案例


  ......



![图片](截图/sc1.png)

![图片](截图/sc2.png)

![图片](截图/sc3.png)

![图片](截图/sc4.png)

