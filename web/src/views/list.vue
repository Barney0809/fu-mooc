<template>
  <main role="main">
    <div class="album py-5 bg-light">
      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <pagination ref="pagination" v-bind:list="listCourse"></pagination>
          </div>
        </div>
        <br>
        <div class="row">
          <div v-for="o in courses" class="col-md-4">
            <the-course v-bind:course="o"></the-course>
          </div>
          <h3 v-show="courses.length === 0">课程还未上架</h3>
        </div>
      </div>
    </div>

  </main>
</template>

<script>
import TheCourse from "../components/the-course";
import Pagination from "@/components/pagination.vue";

export default {
  components: {Pagination, TheCourse},
  name: 'list',
  data: function () {
    return {
      courses: [],
    }
  },
  mounted() {
    let _this = this;
    _this.$refs.pagination.size = 1;
    _this.listCourse(1);
  },
  methods: {
    /**
     * 查询课程列表
     */
    listCourse(page) {
      let _this = this;
      _this.$axios.post(process.env.VUE_APP_SERVER + '/business/web/course/list', {
        page: page,
        size: _this.$refs.pagination.size,
      }).then((response) => {
        if (response.data.code === "0") {
          _this.courses = response.data.data.list;
          _this.$refs.pagination.render(page, response.data.data.total);

        }
      }).catch((response) => {
        console.log("error：", response);
      })
    },

  }
}
</script>
<style>

</style>