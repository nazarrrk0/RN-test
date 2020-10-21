import { requireNativeComponent, NativeModules, UIManager, StyleSheet } from 'react-native';
import React, { Component } from 'react';
import PropTypes from "prop-types";
var AntourageIOSConsts = UIManager.AntourageView.Constants;

var styles = StyleSheet.create({
    antourage: {
        height: AntourageIOSConsts.ComponentHeight,
        width: AntourageIOSConsts.ComponentWidth,
    },
});
const RNAntourage = requireNativeComponent('AntourageView');

export default class AntourageView extends Component {
    static propTypes = {
        widgetPosition: PropTypes.string,
        widgetMargins: PropTypes.object,
        onViewerAppear: PropTypes.func,
        onViewerDisappear: PropTypes.func
    };

    render() {
        const { widgetPosition, widgetMargins, onViewerAppear, onViewerDisappear } = this.props;
        return (
            <RNAntourage
                style={styles.antourage}
                widgetPosition={widgetPosition}
                onViewerAppear={onViewerAppear}
                onViewerDisappear={onViewerDisappear}
                widgetMargins={widgetMargins}
                ref={ref => this.ref = ref}
            />
        );
    }
}


export const Antourage = NativeModules.RNAntourage;

