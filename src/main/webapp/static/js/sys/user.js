var $ = layui.jquery,
	layer = layui.layer, //获取当前窗口的layer对象
	laytpl = layui.laytpl;

var userPage = {
	init: function () {
		userPage.initUpload('#jq_file', '/user/upload');
		return userPage.initTable("#jq_table");
	},
	initTable: function (selectId) {
		return paging.init({
			el: selectId,
			formatter: function (field, data) {
				if ("account" == field) {
					return this.html(data.account);
				}
				if ("roleId" == field) {
					var that = this;
					if ($.data(paging, "roleMap")) {
						var list = $.data(paging, "roleMap");
						for (var i in list) {
							if (data.roleId == list[i].id) {
								return that.html(list[i].name);
							}
						}
					} else {
						$.ajax({
							type: "get",
							url: "/role/queryList",
							dataType: "json",
							async: true,
							success: function (json) {
								var list = json.data;
								$.data(paging, "roleMap", list);
								for (var i in list) {
									if (data.roleId == list[i].id) {
										return that.html(list[i].name);
									}
								}
							}
						});
					}

				}
				if ("operate" == field) {
					var that = this;
					return this.html('<a href="javascript:;" data-id="' + data.id + '" onclick="addPage.open.call(this,true);" class="layui-btn layui-btn-mini">编辑</a>' +
						'<a href="javascript:;" data-id="' + data.id + '" class="layui-btn layui-btn-danger layui-btn-mini">删除</a>');
				}
			}
		});
	},
	initUpload: function (selectId, url) {
		var winIndex;
		layui.upload({
			elem: selectId,
			url: url,
			before: function (input) {
				winIndex = layer.load();
			},
			success: function (res) {
				layer.close(winIndex);
				userPage.reload();
			}
		});
	},
	reload: function () {
		page.reload({
			params: { //发送到服务端的参数
				pageIndex: page.config.params.pageIndex,
				pageSize: page.config.params.pageSize
			}
		});
	},
	onSearch: function () {
		page.reload({
			params: {
				pageIndex: 1,
				account: userPage.getSearchParams()
			}
		})
	},
	getSearchParams: function () {
		return $("#searchValue").val();
	}
};

var page = userPage.init();

var addPage = {
	open: function (isEdit) {
		var addBoxIndex = -1;
		if (addBoxIndex !== -1)
			return;

		var that = $(this);
		//本表单通过ajax加载 --以模板的形式，当然你也可以直接写在页面上读取
		var url = isEdit ? './user-edit-form.html' : './user-add-form.html';
		$.get(url, null, function (content) {
			addBoxIndex = layer.open({
				type: 1,
				title: isEdit ? '编辑' : '添加用户',
				content: content,
				btn: ['保存', '取消'],
				shade: false,
				offset: ['50px', '20%'],
				area: ['600px', '720px'],
				zIndex: 19950924,
				maxmin: true,
				yes: function (index) {
					//触发表单的提交事件
					$('form.layui-form').find('button[lay-filter=edit]').click();
				},
				full: function (elem) {
					var win = window.top === window.self ? window : parent.window;
					$(win).on('resize', function () {
						var $this = $(this);
						elem.width($this.width()).height($this.height()).css({
							top: 0,
							left: 0
						});
						elem.children('div.layui-layer-content').height($this.height() - 95);
					});
				},
				success: function (layero, index) {
					layero.find("#roleId").select({});
					if (isEdit) {
						var thatParent = that.parent().parent();
						layero.find("#id").val(that.attr("data-id"));
						layero.find("#name").val(thatParent.find("td[data-name]").text());
						layero.find("#account").val(thatParent.find("td[data-account]").text());
						var roleId = thatParent.find("td[data-roleId]").attr("data-roleId");
						layero.find("#roleId > option[value='" + roleId + "']").attr("selected", true);
					}
					addPage.renderForm(isEdit, addBoxIndex);
				},
				end: function () {
					addBoxIndex = -1;
				}
			});
		});
	},
	renderForm: function (isEdit, addBoxIndex) {
		//弹出窗口成功后渲染表单
		var form = layui.form();
		form.render();
		form.on('submit(edit)', function (data) {
			/*console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
			 console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
			 console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}*/
			//这里可以写ajax方法提交表单
			var subUrl = isEdit ? "/user/update" : "/user/insert";
			var params = {
				name: data.field.name,
				account: data.field.account,
				password: data.field.password,
				roleId: +data.field.roleId
			};
			if (isEdit) {
				params.id = data.field.id;
			}
			$.post(subUrl, params, function (result) {
				var json = JSON.parse(result);
				if (json.success) {
					layer.msg(json.msg, {zIndex: 19980924});
					layer.close(addBoxIndex); //此时你只需要把获得的index，轻轻地赋予layer.close即可
					page.reload({
						params: { //发送到服务端的参数
							pageIndex: page.config.params.pageIndex,
							pageSize: page.config.params.pageSize
						}
					});
				} else {
					layer.msg(json.msg, {zIndex: 19980924});
				}
			});
			layer.closeAll();
			return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
		});
	}
};

