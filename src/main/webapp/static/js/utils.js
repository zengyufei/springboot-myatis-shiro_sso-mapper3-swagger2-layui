var $ = layui.jquery;
// 全局异常处理
$.ajaxSetup({
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		XMLHttpRequest.status == 200 && "OK" == XMLHttpRequest.statusText && layer.msg('无权限操作');
	}
});
(function ($) {
	$.extend({
		select: function (option) {
			var default_option = {
				tip: true,
				tipMsg: '请选择',
				async: true,
				type: "GET",
				params: {},
				dataType: "json",
				error: function (e) {
					layer.msg(e);
				},
				dataKey: "data",
				valueKey: "id",
				textKey: "name"
			};
			$("select[url]").each(function (k, v) {
				var that = $(v);
				var url = that.attr("url");
				var async = that.attr("async");

				option = $.extend(true, {}, default_option, option);
				var dataKey = that.attr("dataKey") || option.dataKey;
				var valueKey = that.attr("valueKey") || option.valueKey;
				var textKey = that.attr("textKey") || option.textKey;

				$.ajax({
					url: url,
					type: option.type,
					data: option.params,
					async: async || option.async,
					dataType: option.dataType,
					success: function (json) {
						var dataList;
						if (dataKey) {
							dataList = json[dataKey]
						} else {
							dataList = json;
						}
						var temp = [];
						if (option.tip) {
							temp.push($('<option value="">' + option.tipMsg + '</option>'));
						}
						for (var i in dataList) {
							temp.push($('<option value="' + dataList[i][valueKey] + '">' + dataList[i][textKey] + '</option>'));
						}
						that.html(temp);
					},
					error: option.error
				});
			});
		},
		isBlank: function (str) {
			var b = false;
			if(typeof str !== 'string'){
				throw new Error("isBlank 传入参数不是字符串")
			}
			if(str === undefined || str === ''){
				b = true;
			}
			return b;
		},
		isNotBlank: function (str) {
			return !$.isBlank(str);
		},
		isEmpty: function (array) {
			var b = false;
			if(typeof array !== 'object'){
				throw new Error("isEmpty 传入参数不是数组")
			}
			if(array === undefined || array.length === 0){
				b = true;
			}
			return b;
		},
		isNotEmpty: function (array) {
			return !$.isEmpty(array);
		},
		keyup: function (dom) {
			dom.value = dom.value.replace(/[^\d]/g, '');
		},
		afterpaste: function (dom) {
			dom.value = dom.value.replace(/[^\d]/g, '');
		}
})
}($));

(function ($) {

	$.fn.select = function (option) {
		var default_option = {
			selectId: "",
			tip: true,
			tipMsg: '请选择',
			async: false,
			type: "GET",
			params: {},
			dataType: "json",
			error: function (e) {
				layer.msg(e);
			},
			dataKey: "data",
			valueKey: "id",
			textKey: "name"
		};
		var that = $(this);
		var url = that.attr("url");
		if(!url){
			throw new Error("必须要设置url")
		}
		var async = that.attr("async");

		option = $.extend(true, {}, default_option, option);
		var dataKey = that.attr("dataKey") || option.dataKey;
		var valueKey = that.attr("valueKey") || option.valueKey;
		var textKey = that.attr("textKey") || option.textKey;

		$.ajax({
			url: url,
			type: option.type,
			data: option.params,
			async: async || option.async,
			dataType: option.dataType,
			success: function (json) {
				var dataList;
				if (dataKey) {
					dataList = json[dataKey]
				} else {
					dataList = json;
				}
				var temp = [];
				if (option.tip) {
					temp.push($('<option value="">' + option.tipMsg + '</option>'));
				}
				for (var i in dataList) {
					var selected = "";
					var value = dataList[i][valueKey];
					if(option.selectId && value == option.selectId)
						selected = "selected";
					temp.push($('<option value="' + value + '" '+selected+'>' + dataList[i][textKey] + '</option>'));
				}
				that.html(temp);
			},
			error: option.error
		});
	}
}($));

