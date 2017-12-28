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

import BatchedBridge from "react-native/Libraries/BatchedBridge/BatchedBridge";

const activityStarter = NativeModules.ActivityStarter;
export default class MainComponent extends Component {
  onPressTitle(){
    activityStarter.alert();
  };
  render() {
    var navigationView = (
      <View style={{flex: 1, backgroundColor: '#fff'}}>
        <Text style={{margin: 10, fontSize: 15, textAlign: 'left'}} onPress={this.onPressTitle}>Show Chat</Text>
      </View>
    );
    return (
      <DrawerLayoutAndroid
        drawerWidth={300}
        drawerPosition={DrawerLayoutAndroid.positions.Left}
        renderNavigationView={() => navigationView}
        drawerBackgroundColor="rgba(0,0,0,0.5)">
          <View style={{flex: 1, alignItems: 'center'}}>
            <Text style={{margin: 10, fontSize: 15, textAlign: 'right'}}>Hello</Text>
            <Text style={{margin: 10, fontSize: 15, textAlign: 'right'}}>World!</Text>
          </View>
      </DrawerLayoutAndroid>
    );
  }
}

AppRegistry.registerComponent('MainComponent', () => MainComponent);