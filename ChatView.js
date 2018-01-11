import {requireNativeComponent, ViewPropTypes} from 'react-native';

const iface = {
  name: 'ChatView',
  propTypes: {
    ...ViewPropTypes, // include the default view properties
  },
};

module.exports = requireNativeComponent('MoxtraChatView', iface);