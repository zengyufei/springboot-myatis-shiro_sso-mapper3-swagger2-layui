/**
 * Paging 组件
 * @description 基于laytpl 、laypage、layer 封装的组件
 * @author Van zheng_jinfan@126.com
 * @link http://m.zhengjinfan.cn
 * @license MIT
 * @version 1.0.1
 */
	var $ = layui.jquery,
		layer = parent.layui.layer === undefined ? layui.layer : parent.layui.layer;

	var Paging = function () {
		this.config = {
			el: undefined,
			type: 'GET', //数据的获取方式  get or post
			numb: true,
			params: {
				pageIndex: 1,
				pageSize: 3
			}, //获取数据时传递的额外参数
			openWait: false, //加载数据时是否显示等待框
			paged: true,
			success: undefined, //type:function
			fail: function (msg) { layer.msg(msg, { icon: 2 }); }, //type:function
			complate: undefined, //type:function
			serverError: function (xhr) { //ajax的服务错误
				throwError("错误提示： " + xhr.status + " " + xhr.statusText);
			}
		};
	};
	/**
	 * 版本号
	 */
	Paging.prototype.v = '1.0.2';

	/**
	 * 初始化
	 */
	Paging.prototype.init = function (options) {
		this.get(options);
		//分页
		var pagerDiv = $("<div></div>");
		var el = $(this.config.el);
		$(el).after(pagerDiv);
		return this;
	};

	/**
	 * 设置
	 */
	Paging.prototype.set = function (options) {
		var that = this;
		$.extend(true, that.config, options);
		return that;
	};

	/**
	 * 验证
	 */
	Paging.prototype.valid = function () {
		var config = this.config;
		if(config.el === undefined) {
			throwError('Paging Error:请设置目标容器 table!');
		}
		var el = $(config.el);
		if (el.length === 0) {
			throwError('Paging Error:找不到目标容器 table!');
		}
		if (el.attr("url") === undefined) {
			throwError('Paging Error:请配置远程URL!');
		}
		if (config.paged !== undefined && typeof config.paged != 'boolean') {
				throwError('Paging Error:请正确配置参数paged!');
		}
		if (config.type.toUpperCase() !== 'GET' && config.type.toUpperCase() !== 'POST') {
			throwError('Paging Error:type参数配置出错，只支持GET或都POST');
		}
	};

	/**
	 * 获取数据
	 */
	Paging.prototype.get = function (options) {
		var that = this;
		that.set(options);
		that.valid();
		this.render();
	};

	/**
	 * 渲染主程序
	 */
	Paging.prototype.render = function () {
		var that = this,
			config = that.config;
		// 容器
		var el = config.el,
			table = $(el),
			url = table.attr("url");

		// loading
		var windowIndex = config.openWait && layer.load(0,{shade:0.5});
		$.ajax({
			type: config.type,
			url: url,
			data: config.params,
			dataType: 'json',
			async: true,
			success: function (result) {
				config.openWait && layer.close(windowIndex); //关闭等待层
				if (result.success) {
					if(!result.data){
						result.data = {
							list: {}
						};
					}
					that.renderTbody(result.data.list);
					//分页
					that.renderPage(result.data);

					if (config.success) {
						config.success(); //渲染成功
					}
				} else {
					if (config.fail) {
						config.fail(result); //获取数据失败
					}
				}
				if (config.complate) {
					config.complate(); //渲染完成
				}

				if (config.getAllCheckbox) {
					config.getAllCheckbox(table.find("tbody tr").find("td:first input:checkbox")); //渲染完成
				}
			}
		});
	};

	/**
	 * 渲染tbody
	 */
	Paging.prototype.renderTbody = function (dataList) {
		var that = this,
			config = that.config,
			table = $(config.el),
			tbody = table.find("tbody").remove(),
			th = table.find("thead tr th");

		tbody = $("<tbody></tbody>");
		table.append(tbody);

		if(!dataList || !dataList.length || dataList.length == 0){
			tbody.append("<tr><td colspan='" + th.length + "' align='center'>无数据</td></tr>");
			return false;
		}

		// 序号
		var startIndex = 1;
		if(config.numb){
			startIndex = (config.params.pageIndex-1) * config.params.pageSize ;
		}

		var firstTh = th.first();
		var isCheck = firstTh.attr("checkbox");
		if(isCheck){
			var thCheckbox = $('<input type="checkbox" lay-ignore >').click(function () {
				var checkInput = tbody.find("tr td input:checkbox");
				checkInput.prop("checked", thCheckbox.prop("checked"));
			});
			firstTh.html(thCheckbox);
		}

		for(var i=0; i< dataList.length; i++){
			var tr = $("<tr></tr>");
			tbody.append(tr);
			var dataObj = dataList[i];
			$.each(th, function (k, v) {
				var oneTh = $(v);
				var td = $("<td></td>");
				if(isCheck && oneTh.attr("checkbox")){
					var text = $('<input type="checkbox" lay-ignore >');
					text.val(dataObj[oneTh.attr('checkbox')]);
					td.append(text);
					tr.append(td);
					layui.form().render('checkbox');
				}else if(oneTh.attr("numb")) {
					td.append(++startIndex);
					tr.append(td);
				}else{
					if (oneTh.attr('formatter')) {
						var fieldName = oneTh.attr('formatter');
						td.attr("data-" + fieldName, fieldName);
						td.attr("data-" + fieldName, dataObj[fieldName]);
						config.formatter.apply(td, [fieldName, dataObj, i, k]);
					}

					if (oneTh.attr('field')) {
						var fieldName = oneTh.attr('field');
						td.attr("data-" + fieldName, fieldName);
						td.append(dataObj[fieldName]);
					}
					tr.append(td);
				}
			});
		}
	};

	/**
	 * 渲染分页控件
	 */
	Paging.prototype.renderPage = function (page) {
		var that = this,
			config = that.config,
			container = $(config.el).next(),
			pageSize = config.params.pageSize,
			total = page.total,
			pages = total % pageSize == 0 ? (total / pageSize) : (total / pageSize + 1);

		if (config.paged) {
			var defaults = {
				cont: container,
				curr: config.params.pageIndex,
				pages: pages,
				jump: function (obj, first) {
					//得到了当前页，用于向服务端请求对应数据
					var curr = obj.curr;
					if (!first) {
						that.get({
							params: {
								pageIndex: curr,
								pageSize: pageSize
							}
						});
					}
				}
			};
			layui.laypage(defaults);
		}
	};

	/**
	 * 刷新
	 */
	Paging.prototype.reload = function (options) {
		return paging.get(options);
	};
	/**
	 * 抛出一个异常错误信息
	 * @param {String} msg
	 */
	function throwError(msg) {
		throw new Error(msg);
	}

	var paging = new Paging();