# YRxBus
========
# introduce

RxBus ，当你的项目中用到了rxjava ，同时又有跨界面的响应式需求，而又嫌麻烦、不想再添加更多的库，则可以使用这个库，使用方法与eventbus 相同。

看过一些别人写的Rxbus ， 
@Subscribe( 
thread = EventThread.IO, 
tags = { 
@Tag(BusAction.EAT_MORE) 
} 
增加了一个tag ，本身RxJava 就封装了ofType（）用来实现响应，而这个tag又要通过注解重新遍历一遍注解过的方法，得到tag 标记，再主动选择方法用于响应，这样就几乎让oftype（）失去了它的作用。

还有的库增加了别的注解，我觉得完全没有必要，像eventbus 那样简简单单，好理解、好使用不是很好嘛。

接下来重点，个人写的RxBus清纯不做作。



# include lib

    Gradle:
    compile 'com.yan.rxbus:rxbus:1.0.8'
  
    maven:
    <dependency>
      <groupId>com.yan.rxbus</groupId>
      <artifactId>rxbus</artifactId>
      <version>1.0.8</version>
      <type>pom</type>
    </dependency> 
    
    Ivy:
    <dependency org='com.yan.rxbus' name='rxbus' rev='1.0.8'>
    <artifact name='$AID' ext='pom'></artifact>
    </dependency>

# how to use
  
    // 普通响应发送  
    RxBus.getInstance().post(AnyType);
    
    // Sticky发送  
    RxBus.getInstance().postSticky(AnyType);
  
    // 注册
    @Override
      protected void onCreate(Bundle savedInstanceState) {
        ...
        RxBus.getInstance().register(this);
    }
    
    // 注销
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unRegister(this);
    }

    // 响应
    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void show(AnyType str) {
        Toast.makeText(this, str+"", Toast.LENGTH_SHORT).show();
    }


## LICENSE

    Copyright 2016 yan

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

