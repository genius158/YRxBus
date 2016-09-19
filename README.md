# YRxBus
========
# introduce
RxBus 使用与eventbus 相同。

# how to use
  
    //普通响应发送  
    RxBus.getInstance().post(AnyType anyType);
    
    //Sticky发送  
    RxBus.getInstance().postSticky(AnyType anyType);
  
    //注册
    @Override
      protected void onCreate(Bundle savedInstanceState) {
        ...
        RxBus.getInstance().register(this);
    }
    
    //注销
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unRegister(this);
    }

    //响应
    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void show(Action.Action1 str) {
        Toast.makeText(this, str.getStr(), Toast.LENGTH_SHORT).show();
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


