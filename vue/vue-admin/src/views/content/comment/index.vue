<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form
          v-show="showSearch"
          ref="queryForm"
          :model="queryParams"
          :inline="true"
          label-width="68px"
        >
          <el-form-item label="状态" prop="status">
            <el-select
              v-model="queryParams.status"
              placeholder="评论状态"
              clearable
              size="small"
              style="width: 240px"
            >
              <el-option :key="1" label="正常" :value="1" />
              <el-option :key="0" label="未审核" :value="0" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="handleQuery"
            >搜索</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              @click="handleDelete"
            >删除</el-button>
          </el-col>
        </el-row>

        <el-table :data="commentList" style="width: 100%" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" />
          <el-table-column prop="id" label="id" align="center" />
          <el-table-column prop="name" label="评论文章/友链" align="center" />
          <el-table-column prop="username" label="评论人名称" align="center" />
          <el-table-column prop="content" label="评论内容" align="center" />
          <el-table-column prop="createTime" label="评论时间" align="center" />
          <el-table-column prop="status" label="审核状态" align="center">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.status == 0" type="danger">未审核</el-tag>
              <el-tag v-if="scope.row.status == 1" type="success">正常</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                v-hasPermission="['content:link:remove']"
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
              >删除</el-button>
              <el-button
                v-if="scope.row.status == 0"
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleChangeStatus(scope.row, '1')"
              >审核通过</el-button>
<!--              <el-button-->
<!--                v-if="scope.row.status == 1"-->
<!--                size="mini"-->
<!--                type="text"-->
<!--                icon="el-icon-delete"-->
<!--                @click="handleChangeStatus(scope.row, '0')"-->
<!--              >审核不通过</el-button>-->
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-pagination
      :page-size.sync="queryParams.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      :page-sizes="[10, 20, 30, 40]"
      :current-page.sync="queryParams.pageNum"
      @current-change="getList"
      @size-change="getList"
    />
  </div>
</template>

<script>
import { listComment, delComment, changeCommentStatus } from '@/api/content/comment'
// import {changeLinkStatus} from "@/api/content/link";

export default {
  name: 'Category',
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 评论数据
      commentList: null,
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        // 文章名或者为友链评论
        name: null,
        // 评论人名称
        username: null,
        // 创建时间
        createTime: null,
        // 评论内容
        content: null,
        // 未审核，审核通过
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 查询分类列表 */
    getList() {
      this.loading = true
      listComment(this.queryParams).then(response => {
        this.commentList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除评论编号为"' + ids + '"的数据项？').then(function() {
        return delComment(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    // 用户状态修改
    handleChangeStatus(link, newStatus) {
      this.loading = true
      changeCommentStatus(link.id, newStatus).then((response) => {
        this.$modal.msgSuccess('审核成功')
        this.open = false
        this.getList()
      })
    }
    // ,
    // handleStatusChange(row) {
    //   const text = row.status === '0' ? '停用' : '启用'
    //   this.$modal
    //     .confirm('该评论是否通过审核')
    //     .then(function() {
    //       return changeCommentStatus(row.id, row.status)
    //     })
    //     .then(() => {
    //       this.$modal.msgSuccess(text + '成功')
    //     })
    //     .catch(function() {
    //       row.status = row.status === '0' ? '1' : '0'
    //     })
    // }
  }
}
</script>
