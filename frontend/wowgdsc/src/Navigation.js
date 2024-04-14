import { createStackNavigator } from '@react-navigation/stack';

const Stack = createStackNavigator();

const MyStack = () => {
  return (
    <Stack.Navigator
      screenOptions={{
        transitionSpec: {
          open: { duration: 300, easing: Easing.out(Easing.ease) },
          close: { duration: 200, easing: Easing.in(Easing.ease) },
        },
      }}
    >
      
    </Stack.Navigator>
  );
};
