# InputVerifyEdittext
仿支付宝微信密码输入框

- 项目简介

> 这个项目效果是仿照微信支付宝的密码输入框来做的，用 kotlin 书写，实现的功能就是看起来
输入框是分开的，可以定位到某一个输入框进行输入，并且可以从当前输入框一直删除到最后一个输入框
具体的请看文末的 gif 图

- 使用介绍

> 可以直接在项目中引入 VerificationCodeView 这个自定义控件，然后添加 OnInputListener
这个输入完成的监听，输入完成的标准就是6个输入框都被填满；而后可以通过 getInputContent 
方法获取到用户输入的内容

- 控件介绍

> VerificationCodeView 这个类实现了所有的功能，原理还是用了 6 个输入框，如果项目需要可以
在此基础上做拓展，具体的功能实现逻辑是对输入框添加删除键点击监听，以及输入监听

![img](https://github.com/pangxiaohe/InputVerifyEdittext/blob/master/app/src/main/res/drawable/device_2019_07_29_170756.gif)
