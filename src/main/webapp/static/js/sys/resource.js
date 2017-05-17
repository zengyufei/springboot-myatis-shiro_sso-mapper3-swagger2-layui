var setting = {
	view: {
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom,
		selectedMulti: false
	},
	edit: {
		drag: {
			autoExpandTrigger: true,
			prev: dropPrev,
			inner: dropInner,
			next: dropNext,
			isCopy: false,
			isMove: true
		},
		enable: true,
		editNameSelectAll: true,
		showRemoveBtn: showRemoveBtn,
		showRenameBtn: showRenameBtn
	},
	data: {
		simpleData: {
			enable: true,
			idKey: "id",
			pIdKey: "pid",
			rootPId: 0
		}
	},
	callback: {
		beforeDrag: beforeDrag,
		beforeDragOpen: beforeDragOpen,
		beforeDrop: beforeDrop,
		beforeEditName: beforeEditName,
		beforeRemove: beforeRemove,
		beforeRename: beforeRename,
		onDrag: onDrag,
		onDrop: onDrop,
		onRemove: onRemove,
		onRename: onRename,
		onRightClick: onRightClick
	}
};

function beforeDrag(treeId, treeNodes) {
	return false;
}
function beforeEditName(treeId, treeNode) {
	className = (className === "dark" ? "" : "dark");
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	console.log(treeNode)
	edit(true, treeNode);
	/*setTimeout(function () {
	 if (confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？")) {
	 setTimeout(function () {
	 zTree.editName(treeNode);
	 }, 0);
	 }
	 }, 0);*/
	return false;
}
function beforeRemove(treeId, treeNode) {
	className = (className === "dark" ? "" : "dark");
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	var isDelete = false;
	if (confirm("确认删除 -- " + treeNode.name + " 资源吗？")) {
		$.ajax({
			type: "get",
			async: false,
			url: "/resource/delete/" + treeNode.id,
			success: function (data) {
				layui.use(['layer'], function () {
					var layer = layui.layer;
					var json = JSON.parse(data);
					if (json.success) {
						layer.msg(json.msg);
						return true;
					} else {
						layer.msg(json.error);
						return false;
					}
				});
			}
		});
	}else{
		return false;
	}
}

function beforeRename(treeId, treeNode, newName, isCancel) {
	className = (className === "dark" ? "" : "dark");
	if (newName.length == 0) {
		setTimeout(function () {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.cancelEditName();
			alert("节点名称不能为空.");
		}, 0);
		return false;
	}
	return true;
}

function onRemove(e, treeId, treeNode) {
}

function onRename(e, treeId, treeNode, isCancel) {
	var params = {
		pid: treeNode.pid,
		name: treeNode.name,
		resLevel: (+treeNode.level) + 1
	};
	$.post("/resource/insert", params, function (data) {
		var json = JSON.parse(data);
		layui.use(function () {
			var layer = layui.layer;
			layer.msg(json.msg);
		});
		if (json.success) {
			$.extend(true, treeNode, params);
		} else {

		}
	});
}
function showRemoveBtn(treeId, treeNode) {
	return treeNode.level > 0 || !treeNode.isFirstNode;
}
function showRenameBtn(treeId, treeNode) {
	return treeNode.level > 0 || !treeNode.isLastNode;
}
var newCount = 1;
function addHoverDom(treeId, treeNode) {
	if (treeNode.level >= 2)
		return false;
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='add node' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_" + treeNode.tId);
	if (btn) btn.bind("click", function () {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var createNodeArray = zTree.addNodes(treeNode, {
			id: (100 + newCount),
			pId: treeNode.id,
			name: "new node" + (newCount++)
		});
		zTree.editName(createNodeArray[0]);
		return false;
	});
};

function removeHoverDom(treeId, treeNode) {
	$("#addBtn_" + treeNode.tId).unbind().remove();
};

function selectAll() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
}


function dropPrev(treeId, nodes, targetNode) {
	var pNode = targetNode.getParentNode();
	if (targetNode.level == 0) { // 禁止移动到父节点前面
		return false;
	} else if (pNode && pNode.dropInner === false) {
		return false;
	} else {
		for (var i = 0, l = curDragNodes.length; i < l; i++) {
			var curPNode = curDragNodes[i].getParentNode();
			if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
				return false;
			}
		}
	}
	return true;
}

function dropInner(treeId, nodes, targetNode) {
	if (targetNode == null) { // 禁止移动到父节点后面
		return false;
	} else if (targetNode && targetNode.dropInner === false) {
		return false;
	} else if (targetNode && targetNode.resLevel == 3) {
		return false;
	} else {
		for (var i = 0, l = curDragNodes.length; i < l; i++) {
			if (!targetNode && curDragNodes[i].dropRoot === false) {
				return false;
			} else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
				return false;
			}
		}
	}
	return true;
}
function dropNext(treeId, nodes, targetNode) {
	var pNode = targetNode.getParentNode();
	if (targetNode.level == 0) { // 禁止移动到父节点后面
		return false;
	} else if (pNode && pNode.dropInner === false) {
		return false;
	} else {
		for (var i = 0, l = curDragNodes.length; i < l; i++) {
			var curPNode = curDragNodes[i].getParentNode();
			if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
				return false;
			}
		}
	}
	return true;
}
var ztree, rMenu, className = "dark", curDragNodes, autoExpandNode;
function beforeDrag(treeId, treeNodes) {
	className = (className === "dark" ? "" : "dark");
	for (var i = 0, l = treeNodes.length; i < l; i++) {
		if (treeNodes[i].drag === false) {
			curDragNodes = null;
			return false;
		} else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
			curDragNodes = null;
			return false;
		}
	}
	curDragNodes = treeNodes;
	return true;
}

function beforeDragOpen(treeId, treeNode) {
	autoExpandNode = treeNode;
	return true;
}
function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
	className = (className === "dark" ? "" : "dark");
	return true;
}
function onDrag(event, treeId, treeNodes) {
	className = (className === "dark" ? "" : "dark");
}
function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	className = (className === "dark" ? "" : "dark");
	if(targetNode){
		var id = treeNodes[0].id;
		var pid = targetNode && targetNode.id || 0; // prev
		if(moveType == "prev" || moveType == "next"){
			pid = targetNode && targetNode.pid || 0;
		}
		var resLevel = targetNode && (+targetNode.resLevel) + 1;
		$.get("/resource/update", {
			pid: pid,
			id: id,
			resLevel: resLevel
		}, function (data) {
			var json = JSON.parse(data);
			layui.use(['layer'], function () {
				var layer = layui.layer;
				if (json.success) {
					layer.msg(json.msg);
				} else {
					layer.msg(json.error);
				}
			});
		})
	}
}
function onExpand(event, treeId, treeNode) {
	if (treeNode === autoExpandNode) {
		className = (className === "dark" ? "" : "dark");
	}
}
function setTrigger() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.setting.edit.drag.autoExpandTrigger = $("#callbackTrigger").attr("checked");
}

function onRightClick(event, treeId, treeNode) {
	if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
		ztree.cancelSelectedNode();
		showRMenu("root", event.clientX, event.clientY);
	} else if (treeNode && !treeNode.noR) {
		ztree.selectNode(treeNode);
		showRMenu("node", event.clientX, event.clientY);
	}
}
function showRMenu(type, x, y) {
	$("#rMenu ul").show();
	rMenu.css({"top": y + "px", "left": x + "px", "visibility": "visible"});
	$("body").bind("mousedown", onBodyMouseDown);
}
function hideRMenu() {
	if (rMenu) rMenu.css({"visibility": "hidden"});
	$("body").unbind("mousedown", onBodyMouseDown);
}
function onBodyMouseDown(event) {
	if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
		rMenu.css({"visibility": "hidden"});
	}
}
function addTreeNode() {
	var createNodeArray = [];
	hideRMenu();
	var newNode = {name: "new node" + (newCount++)};
	if (ztree.getSelectedNodes()[0]) {
		newNode.checked = ztree.getSelectedNodes()[0].checked;
		var parentNode = ztree.getSelectedNodes()[0];
		if (typeof parentNode.parentIds != "undefined")
			newNode["parentIds"] = parentNode.parentIds + parentNode.id + ",";
		else
			newNode["parentIds"] = parentNode.id + ",";
		createNodeArray = ztree.addNodes(parentNode, newNode);
	} else {
		createNodeArray = ztree.addNodes(null, newNode);
	}
	ztree.editName(createNodeArray[0]);
}


$(function () {
	$.get("/resource/queryList", function (data) {
		var json = JSON.parse(data);
		if (json.success && json.data) {
			$.fn.zTree.init($("#treeDemo"), setting, json.data);
			ztree = $.fn.zTree.getZTreeObj("treeDemo");
			ztree.expandAll(true);
			rMenu = $("#rMenu");
		}
	});

	//$("#callbackTrigger").bind("change", {}, setTrigger);
});
//-->


var addBoxIndex = -1;
function edit(isEdit, treeNode) {
	layui.use(['form', 'laytpl'], function () {
		var $ = layui.jquery,
			layer = layui.layer, //获取当前窗口的layer对象
			laytpl = layui.laytpl,
			form = layui.form();

		if (addBoxIndex !== -1)
			return;
		//本表单通过ajax加载 --以模板的形式，当然你也可以直接写在页面上读取
		$.get('./resource-edit-form.html', null, function (content) {
			addBoxIndex = layer.open({
				type: 1,
				title: (isEdit ? '编辑' : '新增') + '权限菜单',
				content: content,
				btn: ['保存', '取消'],
				shade: 0.6,
				offset: ['100px', '30%'],
				area: ['600px', '400px'],
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
					if (treeNode) {
						layero.find("#id").val(treeNode.id);
						layero.find("#name").val(treeNode.name);
						layero.find("#url").val(treeNode.url);
						layero.find("#resIcon").val(treeNode.resIcon);
						layero.find("#permission").val(treeNode.permission);
						layero.find("#resLevel").val(treeNode.resLevel);
					}
					/*$.get("/role/queryList", function (data) {
					 var json = JSON.parse(data);
					 if (json.success && json.data) {
					 var tpl = laytpl(roleSelect.innerHTML);
					 var result = tpl.render(json.data);
					 layero.find("#roleId").append(result);
					 }
					 });*/
					//弹出窗口成功后渲染表单
					var form = layui.form();
					form.render();
					form.on('submit(edit)', function (data) {
						/*console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
						 console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
						 console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}*/
						//这里可以写ajax方法提交表单
						console.log(data);
						var url = isEdit ? "/resource/update" : "/resource/insert";
						$.post(url, {
							id: data.field.id,
							name: data.field.name,
							url: data.field.url,
							resIcon: data.field.resIcon,
							permission: data.field.permission,
							resLevel: +data.field.resLevel
						}, function (result) {
							var json = JSON.parse(result);
							if (json.success) {
								if (treeNode)
									$.extend(true, treeNode, data.field);
								layer.msg(json.msg, {zIndex: 19980924});
								layer.close(addBoxIndex); //此时你只需要把获得的index，轻轻地赋予layer.close即可
							} else {
								layer.msg(json.msg, {zIndex: 19980924});
							}
						});
						return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
					});
				},
				end: function () {
					addBoxIndex = -1;
				}
			});
		});
	});

}
