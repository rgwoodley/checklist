<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<%@ include file="/WEB-INF/jsp/dashboard/dashboard-templates.tpl" %>

<link href="css/cl-data-table.css" rel="stylesheet" type="text/css">
<div class="data-table" id="activechecklists" style="width:96%">
<h1>Active Checklists</h1>
<table>
<thead>
<tr>
    <th width="15">&nbsp;</th>
	<th><a href="#" id="activechecklist-sort|name">Name</a></th>
	<th>Status</th>
	<th>Current Step</th>
	<th><a href="#" id="activechecklist-sort|owner.name">Owner</a></th>
	<th>Est Complete</th>
</tr>
</thead>
<tbody>
</tbody>
<tfoot>
</tfoot>
</table>
</div>
<div class="data-table" id="recentchecklists">
<h1>Recently Executed Checklists</h1>
<table>
<thead>
<tr>
    <th width="15">&nbsp;</th>
	<th><a href="#" id="recentchecklist-sort|name">Name</a></th>
	<th>Status</th>
	<th>Completed</th>
</tr>
</thead>
<tbody>
</tbody>
<tfoot>
</tfoot>
</table>
</div>
<div class="data-table" id="mychecklists">
<h1>My Checklists</h1>
<table>
<thead>
<tr>
	<th><a href="#" id="mychecklist-sort|name">Name</a></th>
	<th><a href="#" id="mychecklist-sort|numSteps"># Steps</a></th>
	<th><a href="#" id="mychecklist-sort|owner.name">Owner</a></th>
	<th><a href="#" id="mychecklist-sort|expectedDurationInMinutes">Est Duration</a></th>
</tr>
</thead>
<tbody>
</tbody>
<tfoot>
</tfoot>
</table>
</div>
<div style='clear:both;'></div>
<script src="js/cl-paginator.js"></script>
<script src="js/dashboard/active-checklists.js"></script>
<script src="js/dashboard/recent-checklists.js"></script>
<script src="js/dashboard/my-checklists.js"></script>