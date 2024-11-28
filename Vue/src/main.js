import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// 导入element-ui包
import ElementUI from 'element-ui'
// 导入css样式
import 'element-ui/lib/theme-chalk/index.css'
import instance from'./utils/request'

// 使用element插件
Vue.use(ElementUI)

Vue.directive('title', {
  inserted: (el) => {
    document.title = el.dataset.title
  }
})

const cors = require("cors");


Vue.config.productionTip = false








/* v-md-editor 编辑器 start  */
/* 1、v-md-editor 基础引用  */
import VueMarkdownEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';
import Prism from 'prismjs';
VueMarkdownEditor.use(vuepressTheme, {
  Prism,
});
/* 2、v-md-editor 代码块关键字高亮  */
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';
// 引入所有语言包
import hljs from 'highlight.js';
VueMarkdownEditor.use(githubTheme, {
  Hljs: hljs,
});
Vue.use(VueMarkdownEditor);

/* 3、v-md-editor 二次封装  */
import mdEditor from '@/components/MdEditor.vue';
Vue.component('MdEditor', mdEditor);
/* v-md-editor 编辑器 end  */



















new Vue({
  router,
  store,
  instance,
  cors,
  render: h => h(App),
}).$mount('#app')
