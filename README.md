# YRxBus
========
# introduce
RxBus 使用与eventbus 相同。

# include lib

    Gradle:
    compile 'com.yan.rxbus:rxbus:1.0.1'
  
    maven:
    <dependency>
      <groupId>com.yan.rxbus</groupId>
      <artifactId>rxbus</artifactId>
      <version>1.0.1</version>
      <type>pom</type>
    </dependency> 
    
    Ivy:
    <dependency org='com.yan.rxbus' name='rxbus' rev='1.0.1'>
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


