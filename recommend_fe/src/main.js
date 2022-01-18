import Vue from 'vue'
import App from './App.vue'
import VueRouter from "vue-router";
import VueResource from "vue-resource";

Vue.config.debug = true;

Vue.use(VueRouter);
Vue.use(VueResource);

const First = {template: '<div><h2>我是第一个页面</h2></div>'}
import secondcomponent from "./components/secondcomponent";

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
    }
  ]
})

const app = new Vue({
  router: router,
  render: h => h(App)
}).$mount('#app')
