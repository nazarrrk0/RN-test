import React, { Component } from 'react';
import { Platform, StyleSheet, Text, View } from 'react-native';
import AntourageView, { Antourage } from 'react-native-antourage';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

export default class App extends Component {
  componentDidMount() {
    // Antourage.showFeed();
    Antourage.authWithApiKey('apiKey', 'refUserId', 'nickname');
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>Welcome to React Native!</Text>
        <Text style={styles.instructions}>To get started, edit App.js</Text>
        <Text style={styles.instructions}>{instructions}</Text>
        <AntourageView
          onViewerAppear={() => console.log('Appeared')}
          onViewerDisappear={() => console.log('Disappeared')}
          widgetPosition={'bottomLeft'}
          widgetLocale={'en'}
          widgetMargins={{ vertical: 50, horizontal: 20 }}
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
