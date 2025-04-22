<template>
  <div>
    <el-container style=" border: 1px solid #eee;background-color: #373F60">
      <el-header style="font-size: 40px;color:#409EFF">

      </el-header>
      <el-container>
        <el-main>
          <el-container board>
            <el-form :inline="true">
              <el-form-item label="选择目录">
                <el-input v-model="form.path" placeholder="请输入目录">
                </el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="flash()">更新</el-button>
              </el-form-item>
            </el-form>
          </el-container>
          <el-container board>
            <el-form :inline="true">
              <el-form-item label="类型">
                <el-select v-model="form.type" clearable placeholder="请选择">
                  <el-option v-for="String in tableData" :key="String" :label="String" :value="String">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="名称">
                <el-input v-model="form.name" placeholder="请输入名称">
                </el-input>
              </el-form-item>
              <el-form-item label="大小">
                <el-input v-model="form.size" placeholder="请输入文件大小">
                </el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="search()">查询</el-button>
              </el-form-item>
            </el-form>
          </el-container>
          <el-table :data="tableData1" lazy="true">
            <el-table-column label="内容">
              <template slot-scope="scope">
                <template v-if="['mp4', 'mov', 'avi'].includes(scope.row.type)">
                  <video :src="scope.row.src" controls width="80%"></video>
                </template>
                <template v-else-if="['png', 'jpg', 'jpeg'].includes(scope.row.type)">
                  <img :src="scope.row.src" width="80%">
                </template>
                <template v-else>
                  <a :href="scope.row.src" target="_blank">点击查看</a>
                </template>
                <br>
                <b>{{ scope.row.src }}</b>
                <br>
                <b>{{ scope.row.name }}</b>
                <br>
                <b>{{ scope.row.size }}MB</b>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
            layout="total, sizes, prev, pager, next" :page-sizes="[5, 10, 20, 40]" :page-size="pagesize" :total="total"
            :current-page="currentPage">
          </el-pagination>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
import axios from 'axios';
export default {
  data() {
    return {
      tmp: 0,
      tableData: [],
      currentPage: 1, //初始页
      pagesize: 5,
      total: 0,
      tableData1: [],
      information: {
        src: '',
        name: '',
        type: '',
        size: ''
      },
      form: {
        path: '',
        name: '',
        type: '',
        size: ''
      }
    }
  },
  methods: {
    flash() {
      this.tmp = this.currentPage;
      this.currentPage = 1;
      axios.post('/flash', {
        src: this.form.path,
        pagesize: this.pagesize
      }, {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
      }
      ).then((res) => {
        if (res.data.error_num == 0) {
          
          this.tableData1 = res.data.data;
          this.total = res.data.total;
          
        }
        else {
          this.currentPage = this.tmp;
          this.$message.error(res.data.msg);
        }
      })
    },
    search() {
      this.tmp = this.currentPage;
      this.currentPage = 1;
      axios.post('/select', {
        type: this.form.type,
        name: this.form.name,
        size: this.form.size,
        pagesize: this.pagesize
      }, {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
      }
      ).then((res) => {
        if (res.data.error_num == 0) {
          this.tableData1 = res.data.data;
          this.total = res.data.total;
        }
        else {
          this.currentPage = this.tmp;
          this.$message.error(res.data.msg);
        }
      })
    },
    handleSizeChange(val) {
      this.tmp = this.currentPage;
      this.currentPage = 1;

      axios.post('/changePage', {
        currentPage: this.currentPage,
        pagesize: val
      }, {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
      }
      ).then((res) => {
        if (res.data.error_num == 0) {
          
          this.pagesize = val;
          this.tableData1 = res.data.data;
          this.total = res.data.total;
         
        }
        else {
          this.currentPage = this.tmp;
          this.$message.error(res.data.msg);
        }
      })
    },
    handleCurrentChange(val) {
      axios.post('/changePage', {
        currentPage: val,
        pagesize: this.pagesize
      }, {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
      }
      ).then((res) => {
        if (res.data.error_num == 0) {
          this.currentPage = val;
          this.tableData1 = res.data.data;
          this.total = res.data.total;
        }
        else {

          this.$message.error(res.data.msg);
        }
      })
    }
  },
  mounted() {
    axios.get('/ini').then((res) => {

      this.tableData = res.data.data;

    })
  }
}
</script>
<style>
.btn-modern {
  padding: 12px 24px;
  background-color: #174eae;
  /* 主色 */
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
  /* 平滑过渡 */
}

/* 悬停效果 */
.btn-modern:hover {
  background-color: #174eae;
  /* 深色 */
  transform: translateY(-2px);
  /* 轻微上浮 */
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  /* 阴影 */
}

/* 点击效果 */
.btn-modern:active {
  transform: translateY(1px);
}
</style>