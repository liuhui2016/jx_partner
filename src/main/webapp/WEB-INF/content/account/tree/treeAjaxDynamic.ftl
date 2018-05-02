[
<#if (treeRoot)>
	  {
	    label: '${treeRootNode.label}',
	    id: '${treeRootNode.id}',
	    hasChildren: ${(treeRootNode.children.size() > 0)?string}
	  },
<#else>
    <#list treeRootNode.children as node>
	  {
	    label: '${node.label}',
	    id: '${node.id}',
	    hasChildren: ${(node.children.size() > 0)?string}
	  },
	</#list>
</#if>
] 