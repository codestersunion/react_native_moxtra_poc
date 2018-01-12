import React, { Component } from 'react';

import {
  AppRegistry,
  Button,
  NativeEventEmitter,
  NativeModules,
  StyleSheet,
  Text,
  View,
  DrawerLayoutAndroid
} from 'react-native';

import ChatView from './ChatView';

import BatchedBridge from "react-native/Libraries/BatchedBridge/BatchedBridge";

const activityStarter = NativeModules.MyModule;

export default class MainComponent extends Component {
  constructor(props){
    super(props);
    this.onPressTitle = this.onPressTitle.bind(this);
    this.state= {
      changed: false
    };
  }
  onPressTitle(){
    let self = this;
    activityStarter.alert().then(function (d) {
      ChatView = d;
      self.setState({changed: true});
    });
  };
  render() {
    var navigationView = (
      <View style={{flex: 1, backgroundColor: '#fff'}}>
        <Text style={{margin: 10, fontSize: 15, textAlign: 'left'}} onPress={this.onPressTitle}>Show Chat</Text>
      </View>
    );
    return (this.state.changed ? <ChatView /> : <View><Text>Hi</Text></View>);
      // return (
    //   <DrawerLayoutAndroid
    //     drawerWidth={300}
    //     drawerPosition={DrawerLayoutAndroid.positions.Left}
    //     renderNavigationView={() => navigationView}
    //     drawerBackgroundColor="rgba(0,0,0,0.5)">
    //       <View style={{flex: 1, alignItems: 'center'}}>
    //         <Text style={{margin: 10, fontSize: 15, textAlign: 'right'}}>Hello</Text>
    //         <Text style={{margin: 10, fontSize: 15, textAlign: 'right'}}>World!</Text>
    //         {this.state.changed?<ChatView />: null}
    //       </View>
    //   </DrawerLayoutAndroid>
  // );
  }
}

AppRegistry.registerComponent('MainComponent', () => MainComponent);
