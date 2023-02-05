<template>
  <div>
    <p>
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>
    <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>id</th>
        <th>手机号</th>
        <th>验证码</th>
        <th>用途</th>
        <th>生成时间</th>
        <th>用途</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="sms in smss">
        <td>{{sms.id}}</td>
        <td>{{sms.mobile}}</td>
        <td>{{sms.code}}</td>
        <td>{{SMS_USE | optionKV(sms.use)}}</td>
        <td>{{sms.at}}</td>
        <td>{{SMS_STATUS | optionKV(sms.status)}}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import Pagination from "@/components/pagination";

export default {
  components: {Pagination},
  name: "business-sms",
  data: function () {
    return {
      smss: [],
      sms: {},
      SMS_USE: SMS_USE,
      SMS_STATUS: SMS_STATUS,
    }
  },
  mounted: function () {
    let _this = this;
    _this.$refs.pagination.size = 10;
    _this.list(1);
    // sidebar激活样式方法一
    // this.$parent.activeSidebar("business-sms-sidebar");
  },
  methods: {
    /**
     * 点击【新增】
     */
    add() {
      let _this = this;
      _this.sms = {};
      $("#form-modal").modal("show");
    },

    /**
     * 点击【编辑】
     */
    edit(sms) {
      let _this = this;
      _this.sms = $.extend({},sms);
      $("#form-modal").modal("show");
    },

    /**
     * 列表查询
     */
    list(page) {
      let _this = this;
      Loading.show();
      _this.$axios.get(process.env.VUE_APP_SERVER + '/business/admin/sms/list', {
        params: {
          page: page,
          size: _this.$refs.pagination.size
        }
      }).then((response) => {
        Loading.hide();
        console.log("查询短信验证码列表结果：", response);
        _this.smss = response.data.data.list;
        _this.$refs.pagination.render(page, response.data.data.total);
      })
    },

    /**
     * 点击【保存】
     */
    save() {
      let _this = this;

      // 保存校验
        if (1 !== 1
          || !Validator.require(_this.sms.mobile, "手机号")
          || !Validator.length(_this.sms.mobile, "手机号", 1, 50)
          || !Validator.require(_this.sms.code, "验证码")
          || !Validator.require(_this.sms.use, "用途")
          || !Validator.require(_this.sms.at, "生成时间")
          || !Validator.require(_this.sms.status, "用途")
        ) {
          return;
        }

      Loading.show();
      _this.$axios.post(process.env.VUE_APP_SERVER + '/business/admin/sms/save', _this.sms).then((response) => {
        Loading.hide();
        console.log("保存短信验证码列表结果：", response);
        if (response.data.code === "0") {
          $("#form-modal").modal("hide");
          _this.list(1);
          Toast.success("保存成功！");
        } else {
          Toast.warning(response.data.message);
        }
      })
    },

    /**
     * 点击【删除】
     */
    del(id) {
      let _this = this;
      Confirm.show("删除后将不可恢复，确认删除吗？",function (){
        Loading.show();
        _this.$axios.delete(process.env.VUE_APP_SERVER + '/business/admin/sms/delete/' + id).then((response) => {
          Loading.hide();
          console.log("删除短信验证码列表结果：", response);
          if (response.data.code === "0") {
            _this.list(1);
            Toast.success("删除成功！")
          }
        })
      });
    }
  }
}
</script>