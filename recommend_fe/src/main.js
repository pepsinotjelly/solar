import Vue from 'vue'
import App from './App.vue'
import VueRouter from "vue-router";
import VueResource from "vue-resource";
import * as echarts from 'echarts'

Vue.config.debug = true;

Vue.use(VueRouter);
Vue.use(VueResource);
Vue.prototype.$echarts = echarts

const First = {template: '<div><h2>我是第一个页面</h2></div>'}
import secondcomponent from "./components/secondcomponent";
import radarcomponent from "./components/radarcomponent";

const router = new VueRouter({
  mode: 'history',
  base: __dirname,
  routes: [
    {
      path: '/first',
      component: First
    },
    {
      path: '/second',
      component: secondcomponent
    },
    {
      path: '/radar',
      component: radarcomponent
    }
  ]
})

const app = new Vue({
  router: router,
  render: h => h(App)
}).$mount('#app')
