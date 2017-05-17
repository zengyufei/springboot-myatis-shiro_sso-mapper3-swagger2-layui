(function ($) {
	var SEL_LIST = [10, 20, 30, 50],
		BTN_LIST = [
			{type: 'first', text: '<i class="iconfont icon-page-left"></i>首页'},
			{type: 'prev', text: '<i class="iconfont icon-xiangzuo"></i>上一页'},
			{type: 'next', text: '下一页<i class="iconfont icon-xiangyou"></i>'},
			{type: 'last', text: '尾页<i class="iconfont icon-page-right"></i>'}
		],
		DEFULT_CONFIG = {
			pageKey: 'page',
			totalPagesKey: 'totalPages',
			totalCountKey: 'totalCount',
			dataKey: 'dataList',
			pageSize: 10
		};

	$.fn.datagrid = function (options) {

		options = options || {};
		var queryParams = $.extend(true, {}, options.queryParams);

		if (!$.isArray(options)) {
			options = [options];
		}

		var initPagination = function () {
			var dg = this;

			var mod = $('<div class="mod-table"></div>').width(dg.parent().width());

			// 分页选项
			var sel = $('<select class="page-list"><select>');
			$.each(SEL_LIST, function () {
				sel.append('<option value="' + this + '">' + this + '</option>');
			});
			sel.val(this.data('pageSize'));

			sel.on('change', function () {
				sel.val(this.value);

				dg.data('pageSize', this.value);

				load.apply(dg, [{type: 'reload'}]);
			});

			// 分页按钮
			var btn = $('<ul></ul>');
			$.each(BTN_LIST, function () {
				var _this = this;
				btn.append($('<li><a href="javascript:void(0);" class="jq_pages_acls">' + this.text + '</a></li>').on('click', function () {
					load.apply(dg, [{type: _this.type}]);
				}));
			});

			return mod.append($('<div class="table-page"></div>').append(sel).append(btn)).append('<div class="table-infor">当前第1页, 共10记录 / 20页<div>');
		};

		var update = function (dataList) {
			var $this = $(this);
			var dg = this, th = dg.find('th'), tbody = $('<tbody></tbody>');

			dg.find('tbody').remove();
			dg.append(tbody);
			if(!dataList || !dataList.length || dataList.length == 0){
				tbody.append("<tr><td colspan='" + th.length + "'>无数据</td></tr>");
				return false;
			}

			var startIndex = ($this.data('pageNo') -1) * $this.data('pageSize') + 1;
			for (var i = 0; i < dataList.length; i++) {
				var tr = $('<tr></tr>');
				th.each(function (index) {
					var td = $('<td></td>');

					if (this.getAttribute('data-number')) {
						td.append(startIndex++);
					}

					if (this.getAttribute('data-formatter')) {
						dg.data('option').formatter.apply(td, [this.getAttribute('data-formatter'), dataList[i], i, index]);
					}

					if (this.getAttribute('data-field')) {
						td.append(dataList[i][this.getAttribute('data-field')]);
					}

					tr.append(td);
				});

				if (dg.data('option').clickRow) {
					(function (i) {
						tr.on('click', function (e) {
							dg.data('option').clickRow.apply(tr, [i, dataList]);
						})
					}(i));
				}
				if (dg.data('option').rowStyle) {
					tr.css(dg.data('option').rowStyle.apply(dg, [dataList[i]]));
				}
				tbody.append(tr);
			}
		};

		var load = function (config) {
			/* 初始化 */
			var $this = $(this);
			var temp = $.extend(true, {}, $this.data('option'))
			config = $.extend(true, temp, config);
			var isPagination = config.pagination;

			var type = config.type;
			var dataKey = config.dataKey;

			var pn, ps, tp, pageKey, totalPagesKey, totalCountKey;
			if (isPagination) {
				pn = $this.data('pageNo');
				ps = $this.data('pageSize');
				tp = $this.data('totalPages');
				pageKey = config.pageKey;
				totalPagesKey = config.totalPagesKey;
				totalCountKey = config.totalCountKey;
			}

			if (/^(first|prev)$/.test(type) && pn == 1) {
				layer.msg("已是首页",{icon:4,time:1500});
				return false;
			}
			if (/^(next|last)$/.test(type) && pn == tp) {
				layer.msg("已是尾页",{icon:4,time:1500});
				return false;
			}

			if (type == 'reload' || type == 'first') {
				pn = 1;

			} else if (type == 'prev') {
				pn--;

			} else if (type == 'next') {
				pn++;

			} else if (type == 'last') {
				pn = tp;
			}

			if (isPagination) {
				$this.data('pageNo', pn);
				$this.data('pageSize', ps);
			}

			queryParams = isPagination ? ($.extend(true, {}, queryParams, {pn: pn, ps: ps})) : queryParams;

			var windowIndex = layer.load(0,{shade:0.5});
			$.post($this.attr('url'), queryParams, function (resp) {
				var totalPages, totalCount;
				if (isPagination) {
					totalPages = pageKey == '' ? resp[totalPagesKey] : resp[pageKey] ? resp[pageKey][totalPagesKey] : 0;
					totalCount = pageKey == '' ? resp[totalCountKey] : resp[pageKey] ? resp[pageKey][totalCountKey] : 0;
					$this.data('totalPages', totalPages);
					$this.data('totalCount', totalCount);
					$this.parent().parent().find('.table-infor').html('当前第' + pn + '  页, 共' + totalCount + '记录 / ' + totalPages + '页')
				}

				var dataList = isPagination && pageKey != '' ?
					(resp[pageKey] ? resp[pageKey][dataKey] : {}) : resp[dataKey];
				update.apply($this, [dataList]);
				layer.close(windowIndex);
			}, 'JSON').error(function (resp) {
				layer.close(windowIndex);
				layer.alert("查询错误，可能为网络错误，请联系管理员！");
			});
		};

		this.reload = function (config) {

			queryParams = config ? $.extend(true, config.params, config.queryParams) : queryParams;
			this.each(function () {
				load.apply(this, [{type: 'reload'}]);
			});
		};

		return this.each(function (index) {
			var $this = $(this);
			var option = $.extend(true, {}, DEFULT_CONFIG, options[index]);
			$this.data('option', option);
			var isPagination = typeof option.pagination == 'boolean' ?
				option.pagination : option.pagination = true;

			if (isPagination) {
				$this.data('pageNo', 1);
				$this.data('pageSize', $this.data('option').pageSize);
			}

			if (!$this.attr('url')) {
				console && console.error('插件url不能为空，请参考使用说明');
				return;
			}

			// 初始化分页组件
			isPagination &&
			$this.parent().after(initPagination.apply($this));

			load.apply(this, [option]);
		});
	}
}(jQuery));