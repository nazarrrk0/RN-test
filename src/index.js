import {
  requireNativeComponent,
  NativeModules,
  UIManager,
  StyleSheet,
  Platform,
} from 'react-native';
import React, { Component } from 'react';
import PropTypes from 'prop-types';
var AntourageIOSConsts = UIManager.AntourageView.Constants;

var styles = StyleSheet.create({
  antourage: {
    height: Platform.os == 'ios' ? AntourageIOSConsts.ComponentHeight : 100,
    width: Platform.os == 'ios' ? AntourageIOSConsts.ComponentWidth : 100,
  },
});
const RNAntourage = requireNativeComponent('AntourageView');

export default class AntourageView extends Component {
  static propTypes = {
    widgetPosition: PropTypes.string,
    widgetLocale: PropTypes.string,
    widgetMargins: PropTypes.object,
    onViewerAppear: PropTypes.func,
    onViewerDisappear: PropTypes.func,
  };

  render() {
    const {
      widgetLocale,
      widgetPosition,
      widgetMargins,
      onViewerAppear,
      onViewerDisappear,
    } = this.props;
    return (
      <RNAntourage
        style={styles.antourage}
        widgetLocale={widgetLocale}
        widgetPosition={widgetPosition}
        onViewerAppear={onViewerAppear}
        onViewerDisappear={onViewerDisappear}
        widgetMargins={widgetMargins}
        ref={(ref) => (this.ref = ref)}
      />
    );
  }
}

export const Antourage = NativeModules.RNAntourage;
