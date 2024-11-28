<template>
    <div class="welcome-content">
        <!-- 头部 -->
        <div>
            <top></top>
        </div>
        <!-- 主体 -->
        <el-main class="home-main-body">
            <el-card class="edit" style="margin-top: 60px;">
                <MdEditor v-if="initSuccess" :content.sync="obj.textThree" />
            </el-card>
        </el-main>
        <!-- 底部备案 -->
        <div>
            <bottom></bottom>
        </div>
    </div>


</template>

<script>
import MdEditor from '../MdEditor.vue';
import Cookies from "js-cookie";
import axios from "axios";
import Top from "@/components/reception/Top.vue";
import Bottom from "@/components/reception/Bottom.vue";

export default {
    name: "BlogIndex",
    components: { MdEditor },
    data() {
        return {
            initSuccess: false,
            show: '',
            obj: {
                textThree: []
            },
            bookList: Array,
        };
    },
    components: {
        Top, Bottom,
    },
    methods: {
        async loadGood(str) {
            axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";

            await axios
                .post("http://localhost:8888/content/md", { str: str })
                .then(result => {
                    this.bookList = result.data

                    var left=document.getElementsByClassName('v-md-editor__editor-wrapper')
                    console.log(left)

                    //left.style.display='none'

                    this.initSuccess = true;

                    for (let i = 0; i < this.bookList.length; i++) {
                        console.log('aaa' + this.show)
                        this.show += this.bookList[i]
                        
                        if(this.show.endsWith('')){
                            //this.show = this.show + '<br/>'
                            //this.show = this.show + ' '
                        }
                        this.obj.textThree = this.show
                    }



                })
                .catch(function (error) {
                    // 处理错误情况
                    console.log(error);
                });
        },
        init() {
            var md = Cookies.get('www');
            console.log(md);
            var str = 'src/main/resources/' + md;
            this.loadGood(str)
        },

        
    },
    created() {
        this.init()

    }
}
</script>
