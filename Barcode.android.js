/*
 * A smart barcode scanner for react-native apps
 * https://github.com/react-native-component/react-native-smart-barcode/
 * Released under the MIT license
 * Copyright (c) 2016 react-native-component <moonsunfall@aliyun.com>
 */

import React, { Component } from "react";
import PropTypes from "prop-types";
import {
  View,
  requireNativeComponent,
  NativeModules,
  AppState
} from "react-native";

const BarcodeManager = NativeModules.CaptureModule;

class BarcodeView extends Component {
  static defaultProps = {
    barCodeTypes: Object.values(BarcodeManager.barCodeTypes),
    scannerRectWidth: 255,
    scannerRectHeight: 255,
    scannerRectTop: 0,
    scannerRectLeft: 0,
    scannerLineInterval: 3000,
    scannerRectCornerColor: `#09BB0D`
  };

  static propTypes = {
    ...View.propTypes,
    onBarCodeRead: PropTypes.func.isRequired,
    onAuthorized: PropTypes.func,
    barCodeTypes: PropTypes.array,
    scannerRectWidth: PropTypes.number,
    scannerRectHeight: PropTypes.number,
    scannerRectTop: PropTypes.number,
    scannerRectLeft: PropTypes.number,
    scannerLineInterval: PropTypes.number,
    scannerRectCornerColor: PropTypes.string
  };

  render() {
    return <NativeBarCode {...this.props} />;
  }

  componentDidMount() {
    AppState.addEventListener("change", this._handleAppStateChange);
  }
  componentWillUnmount() {
    AppState.removeEventListener("change", this._handleAppStateChange);
  }

  startScan() {
    BarcodeManager.startSession();
  }

  stopScan() {
    BarcodeManager.stopSession();
  }

  _handleAppStateChange = currentAppState => {
    // 因为业务需求, 需要手动启动扫描
    if (currentAppState !== "active") {
      this.stopScan();
    }
  };
}

const NativeBarCode = requireNativeComponent("CaptureView", BarcodeView);

export default BarcodeView;

export const getQRCodeFromImage = (path, onFail, onSuccess) => {
  // android 需要调整路径, 移除无法识别的部分
  const imagePath = path.replace("file:///", "/");
  NativeModules.CaptureModule.DecodeFromPath(imagePath, onFail, onSuccess);
};
