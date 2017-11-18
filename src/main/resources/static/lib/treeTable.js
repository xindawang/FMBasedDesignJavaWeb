;
(function(window) {
	function TreeTable($element, opt) {
		this.$element = $element;
		this.defaults = {
			url : null,
			afterDraw : function() {
				
			}
		}
		this.options = $.extend({}, this.defaults, opt)
	}
	
	$.fn.treeTable = function(opt) {
		var treeTable = new TreeTable(this, opt)
		return treeTable.init()
	}

	/**
	 * 初始化
	 */
	TreeTable.prototype.init = function() {
		return this.drawHead().get()
	}

	/**
	 * 获取数据
	 */
	TreeTable.prototype.get = function() {
		if (this.options.url != null) {
			$.ajax({
				url : this.options.url,
				dataType : 'json',
				context : this,
				success : function(data) {
					this.options.list = data.list.slice(0)
					this.draw(data.list)
				}
			})
		}
		return this
	}

	/**
	 * 构建表体
	 */
	TreeTable.prototype.draw = function(list) {
		this.clearTbody();
		var tbody = this.$element.find('table>tbody');
		var columns = this.options.columns;
		while (list.length != 0) {
			for (var i=0; i<list.length; ++i) {
				var item = list[i]
				var tr = $('<tr></tr>')
				var dataObject = {
					'data-id' : item.id,
					'data-parent' : item.parent
				}
				tr.attr(dataObject)

				if (tr.data('parent') != null) {
					var parent = tbody.children('tr[data-id="' + tr.data('parent') + '"]')
					if (parent.length == 0) {
						continue
					} else {
						parent.after(tr)
					}
				} else {
					tr.appendTo(tbody)
				}
				list.splice(i--, 1)
				for ( var j in columns) {
					var column = columns[j];
					if (typeof column.data == 'string') {
						var attr = column.data.split('.')
						var data = item
						for ( var k in attr) {
							if (data != null)
								data = data[attr[k]]
						}
						if (data == null || data == "undefined") {
							data = "--"
						}
						tr.append('<td>' + data + '</td>')
					} else if (typeof column.element == 'string') {
						tr.append('<td>' + column.element + '</td>')
					} else {
						tr.append('<td></td>')
					}
					if (typeof column.file == 'string') {						
						var td = tr.children('td:last')
						if (td.text()!="--"){			
							if (td[0].textContent.indexOf('\\')>0||td[0].textContent.indexOf('/')>0){
								var file = td[0].textContent;
								var filepath = file.split('\\')
								filepath = filepath[filepath.length-1].split('/')
								var filename = filepath[filepath.length-1]
								td.text(filename)
								td.addClass("fileValue")
							}						
						}else{	
							if(item.type!=3){
								td.text('')
								td.append('<input class="dataValue"/>')
							}
						}				
					}
					if (typeof column.bool == 'object') {
						var TRUE = column.bool.TRUE
						var FALSE = column.bool.FALSE
						var td = tr.children('td:last')
						var data = td.text();
						if (data == 'true') {
							td.text(TRUE)
						} else if (data == 'false') {
							td.text(FALSE)
						}
					}				
					if (typeof column.number == 'object') {
						var ZERO = column.number.ZERO
						var ONE = column.number.ONE
						var TWO = column.number.TWO
						var THREE = column.number.THREE	
						var FOUR = column.number.FOUR
						var td = tr.children('td:last')
						var data = td.text();
						if (data == '0') {
							td.text(ZERO)
						}else if (data == '1') {
							td.text(ONE)
						}else if (data == '2') {
							td.text(TWO)
						}else if (data == '3') {
							td.text(THREE)
						}else if (data == '4') {
							td.text(FOUR)
						}
					}
				}
			}
		}
		
		tbody.children('tr').each(function(index) {
			for ( var i in columns) {
				var column = columns[i]
				if (typeof column.increment == 'number') {
					$(this).children('td').eq(i).text(index + column.increment)
					$(this).children('td').eq(i).addClass('addList')
				}
				if (typeof column.tree == 'boolean' && column.tree) {
					var td = $(this).children('td').eq(i)
					var content = td.text()
					td.text('')
					var node = $('<div class="tree-node"></div>').appendTo(td)
					var indent
					if ($(this).data('parent') == null) {
						indent = $('<span class="indent"></span>')
					} else {
						var parent = tbody.children('tr[data-id="' + $(this).data('parent') + '"]')
						indent = parent.children('td').eq(i).find('span.indent').clone()
						indent.append('<span class="tree-indent"></span>')
					}
					indent.appendTo(node)
					var hit
					if ($(this).next().data('parent') == $(this).data('id')) {
						hit = $('<span class="tree-hit tree-expanded"></span>')
					} else {
						hit = $('<span class="tree-indent"></span>')
					}
					hit.appendTo(node)
					var title = $('<span class="tree-title"></span>').appendTo(node)
					title.text(content)
				}
			}
			
			$(this).dblclick(function() {
				if ($(this).find('span.tree-collapsed').length > 0) { // 展开当前层次
					$(this).find('span.tree-collapsed').removeClass('tree-collapsed').addClass('tree-expanded');
					$(this).find('span.tree-folder').addClass('tree-folder-open');
					show_sub_lines(this);
				} else if ($(this).find('span.tree-expanded').length > 0) { // 收缩当前层次
					$(this).find('span.tree-expanded').removeClass('tree-expanded').addClass('tree-collapsed');
					$(this).find('span.tree-folder').removeClass('tree-folder-open');
					hide_sub_lines(this);
				}
				return false
			})
		})
		this.options.afterDraw();
		return this
	}

	// 递归调用隐藏子节点
	function hide_sub_lines(current) {
		var val = $(current).data('id');
		var subs = $(current).parent().find('tr[data-parent=' + val + ']');
		if (subs.length > 0) {
			subs.hide();
			$.each(subs, function(index, item) {
				hide_sub_lines(item);
			});
		}
		return;
	}
	
	// 递归调用显示子节点
	function show_sub_lines(current) {
		var val = $(current).data('id');
		if ($(current).find('span.tree-collapsed').length > 0) {
			return;
		}
		var subs = $(current).parent().find('tr[data-parent=' + val + ']');
		if (subs.length > 0) {
			subs.show();
			$.each(subs, function(index, item) {
				show_sub_lines(item);
			});
		}
		return;
	}
	
	/**
	 * 清理当前显示
	 */
	TreeTable.prototype.clearTbody = function() {
		this.$element.find('tbody>tr').remove();
		return this;
	}

	/**
	 * 构建表头
	 */
	TreeTable.prototype.drawHead = function() {
		var table = this.$element.children('table').append('<thead><tr></tr></thead>')
		var columns = this.options.columns
		for ( var i in columns) {
			var column = columns[i]
			var tr = table.find('thead>tr').append('<th>' + column.title + '</th>')
			tr.children('th:last').css('width', column.width)
		}
		table.append('<tbody></tbody>')
		return this;
	}
	
	/**
	 * 新增一行
	 */
	TreeTable.prototype.addRow = function(columns, callback) {
		var $element = this.$element
		var rowTem = '<tr>';
		for ( var i in columns) {
			rowTem += '<td>' + columns[i] + '</td>';
		}
		rowTem += '</tr>';
		
		$element.find('tbody').append(rowTem);
		callback()

		return this;
	}

})(window)