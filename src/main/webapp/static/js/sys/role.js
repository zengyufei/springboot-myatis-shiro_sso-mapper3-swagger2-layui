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
		onClick: onClick,
		onRightClick: onRightClick
	}
};

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
	if(confirm("确认删除 -- " + treeNode.name + " 角色吗？")){
		$.ajax({
			type: "get",
			async: false,
			url: "/role/delete/"+ treeNode.id,
			success: function (data) {
				layui.use(['layer'], function () {
					var layer = layui.layer;
					var json = JSON.parse(data);
					if(json.success){
						layer.msg(json.msg);
						return true;
					}else{
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
		resourceId: treeNode.resourceId
	};
	$.post("/role/insert", params, function (data) {
		var json = JSON.parse(data);
		layui.use(function () {
			var layer = layui.layer;
			layer.msg(json.msg);
		});
		if(json.success){
			treeNode = $.extend(true, treeNode, params, json.data);
			console.log(treeNode)
		}else{

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
	if(treeNode.level >= 10)
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
	if(targetNode && targetNode.level == 0){ // 禁止移动到父节点前面
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
	if(targetNode == null) { // 禁止移动到父节点后面
		return false;
	} else if (targetNode && targetNode.dropInner === false) {
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
	if (pNode && pNode.dropInner === false) {
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
function beforeDrag(treeId, treeNodes, targetNode, moveType) {
	console.log("beforeDrag")
	className = (className === "dark" ? "" : "dark");
	if(!(targetNode == null || (moveType != "prev" && !targetNode.parentTId))){
		return false;
	}
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
	console.log("beforeDragOpen");
	autoExpandNode = treeNode;
	return true;
}
function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
	console.log("beforeDrop")
	className = (className === "dark" ? "" : "dark");
	return true;
}
function onDrag(event, treeId, treeNodes) {
	console.log("onDrag");
	className = (className === "dark" ? "" : "dark");
}
function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	console.log("onDrop");
	className = (className === "dark" ? "" : "dark");
	if(targetNode){
		var id = treeNodes[0].id;
		var pid = targetNode && targetNode.id || 0; // prev
		if(moveType == "prev" || moveType == "next"){
			pid = targetNode && targetNode.pid || 0;
		}
		var ids = recurChildren("", treeNodes[0].children);
		ids = ids.substring(0, ids.lastIndexOf(","));
		$.get("/role/update", {
			pid: pid,
			id: id,
			ids: ids
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
function recurChildren(ids, childrens) {
	for(var i in childrens){
		var treeNode = childrens[i];
		ids = ids + treeNode.id + ",";
		var children = treeNode.children;
		if(children){
			ids =  recurChildren(ids, children);
		}
	}
	return ids;
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

function onClick(event, treeId, treeNode) {
	$.get("/role/queryById/" + treeNode.id, function (data) {
		var json = JSON.parse(data);
		if(json.success){
			openWinManagerResource(event, treeId, treeNode, json.data);
		}
	});
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
	$.get("/role/queryList", function (data) {
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
		$.get('./role-edit-form.html', null, function (content) {
			addBoxIndex = layer.open({
				type: 1,
				title: (isEdit?'编辑':'新增')+'权限菜单',
				content: content,
				btn: ['保存', '取消'],
				shade: 0.6,
				offset: ['100px', '30%'],
				area: ['600px', '300px'],
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
					if(treeNode){
						layero.find("#id").val(treeNode.id);
						layero.find("#name").val(treeNode.name);
						layero.find("#description").val(treeNode.description);
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
						var url = isEdit? "/role/update" : "/role/insert";
						$.post(url, {
							id: data.field.id,
							name: data.field.name,
							description: data.field.description
						}, function (result) {
							var json = JSON.parse(result);
							if (json.success) {
								if(treeNode)
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


function openWinManagerResource(event, treeId, treeNode, role){
	var resourceIds = role.resourceId;
	var myResourceIds;
	if(resourceIds){
		myResourceIds = resourceIds.split(",");
	}

	function onCheck(event, treeId, treeNode) {
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		var nodes = treeObj.getCheckedNodes(true);
		var ids = "";
		for(var i in nodes){
			ids += nodes[i].id + ","
		}
		ids = ids.substring(0, ids.lastIndexOf(","));
		$.post("/role/update", {
			id:treeNode.roleId,
			resourceId: ids
		}, function (data) {
			var json = JSON.parse(data);
			if(json.success && json.data){
			}
		});
	}
	// zTreeObj
	var zTreeObj = function(data) {
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback:{
				onCheck:onCheck
			}
		};

		return $.fn.zTree.init($("#demo"), setting, data);
	};

	$.get("/resource/queryList", function (data) {
		var json = JSON.parse(data);
		if(json.success && json.data){
			json.data.forEach(function (item) {
				if(resourceIds == 'all'){
					item.checked = true;
					item.chkDisabled=true;
				}else{
					myResourceIds && myResourceIds.forEach(function (resourceId) {
						if(item.id == resourceId){
							item.checked = true;
						}
					});
				}
				item.open = true;
				item.roleId = role.id;
				if(item.children){
					item.children.forEach(function (twoItem) {
						if(resourceIds == 'all'){
							twoItem.checked = true;
							twoItem.chkDisabled=true;
						}else{
							myResourceIds && myResourceIds.forEach(function (resourceId) {
								if(twoItem.id == resourceId){
									twoItem.checked = true;
									item.checked = true;
								}
							});
						}
						twoItem.open = true;
						twoItem.roleId = role.id;
						if(twoItem.children){
							twoItem.children.forEach(function (threeItem) {
								if(resourceIds == 'all'){
									threeItem.checked = true;
									threeItem.chkDisabled=true;
								}else{
									myResourceIds && myResourceIds.forEach(function (resourceId) {
										if(threeItem.id == resourceId){
											threeItem.checked = true;
											twoItem.checked = true;
										}
									});
								}
								threeItem.open = true;
								threeItem.roleId = role.id;
							})
						}
					})
				}
			});

			//处理 data
			var newIndex = 0;
			var newMenus = [];
			var menus = json.data;
			for(var i in menus){
				var index = 0;
				var menu = menus[i];
				for(var j in menus){
					if(menu.id == menus[j].pid){
						menu.children[index++] = menus[j];
					}
				}
				if(menu.pid == 0){
					newMenus[newIndex++] = menu;
				}
			}
			// 执行
			zTreeObj(newMenus);

			// layer
			layui.use('layer', function () {
				var $ = layui.jquery,
					layer = layui.layer;
				layer.open({
					type: 1, //page层
					area: ['400px', '80%'],
					title: role.name + ' 角色权限',
					shadeClose: true,
					shade: 0.8, //遮罩透明度
					moveType: 1, //拖拽风格，0是默认，1是传统拖动
					shift: 0, //0-6的动画形式，-1不开启
					content: $(".zTreeObj")
				});
			});
		}else{
		}
	});
}
