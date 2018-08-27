// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import vueResource from 'vue-resource'
import Customers from './components/Customers'
import About from './components/About'
import Add from './components/Add'
import CustomerDetails from './components/CustomerDetails'
import Edit from './components/Edit'

Vue.use(vueResource)
Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  base: __dirname,
  routes: [
    {path: '/', component: Customers},
    {path: '/about', component: About},
    {path: '/add', component: Add},
    {path: '/customer/:id', component: CustomerDetails},
    {path: '/edit/:id', component: Edit}
  ]
});

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  router,
  template: `
     <div id="app">
     <nav class="navbar navbar-expand-md navbar-dark bg-dark">

     <div class="container">
     <div class="navbar-header">
           <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
             <span class="navbar-toggler-icon"></span>
           </button>

           <a class="navbar-brand" href="#">vCustomers</a>
           </div>
           <div class="collapse navbar-collapse" id="navbarsExampleDefault">
             <ul class="navbar-nav mr-auto">
             <li class="nav-item nav-link">
               <router-link to="/">Home</router-link>
             </li>
               <li class="nav-item nav-link">
                 <router-link to="/about">About</router-link>
               </li>
             </ul>
             <ul class="navbar-nav navbar-right">
             <li class="nav-item nav-link">
               <router-link to="/add">Add Customer</router-link>
             </li>
             </ul>
             </div>
           </div>
         </nav>

     <router-view></router-view>
     </div>
  `
}).$mount('#app')
