<script type="text/template" id="my-checklist-template">
<td id="mychecklist-view"><a href="#" id="mychecklist-view"><@- name @></a></td><td id="mychecklist-view"><a href="#" id="mychecklist-view"><@- numSteps @></a></td><td id="mychecklist-view"><a href="#" id="mychecklist-view"><@- ownerName @></a></td><td id="mychecklist-view"><a href="#" id="mychecklist-view"><@- duration @></a></td>
</script>
 
<script type="text/template" id="my-checklist-footer-template">
<td colspan="2" class="results">Page <@- page @> of <@- totalPages @></td>
	<td colspan="2" class="prevnext"><a href="#" id="mychecklist-prev">&lt;&nbsp;Previous</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" id="mychecklist-next">Next&nbsp;&gt;</a></td>
</script>

<script type="text/template" id="my-checklist-noresult-template">
<td colspan="4" class="noresults"><a href="#" id="mychecklist-create">Click here to create a new checklist</a></td>
</script>

<script type="text/template" id="active-checklist-template">
<td id="activechecklist-view"><a href="#" id="activechecklist-view" border="0"><img src="img/<@- statusImageURL @>" width="16" height="16"/></a></td><td><a href="#" id="activechecklist-view"><@- name @></a></td><td><a href="#" id="activechecklist-view"><@- status @></a></td><td><a href="#" id="activechecklist-view"><@- currentStep @></a></td><td><a href="#" id="activechecklist-view"><@- ownerName @></a></td><td><a href="#" id="activechecklist-view"><@- estimatedCompletionTime @></a></td>
</script>
 
<script type="text/template" id="active-checklist-footer-template">
<td colspan="3" class="results">Page <@- page @> of <@- totalPages @></td>
	<td colspan="3" class="prevnext"><a href="#" id="activechecklist-prev">&lt;&nbsp;Previous</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" id="activechecklist-next">Next&nbsp;&gt;</a></td>
</script>

<script type="text/template" id="active-checklist-noresult-template">
<td colspan="6" class="noresults">No actively running checklists found.</td>
</script>

<script type="text/template" id="recent-checklist-template">
<td id="recentchecklist-view"><a href="#" id="recentchecklist-view" border="0"><img src="img/<@- statusImageURL @>" width="16" height="16"/></a></td><td><a href="#" id="recentchecklist-view"><@- name @></a></td><td><a href="#" id="recentchecklist-view"><@- status @></a></td><td><a href="#" id="recentchecklist-view"><@- actualCompletionTime @></a></td>
</script>
 
<script type="text/template" id="recent-checklist-footer-template">
<td colspan="2" class="results">Page <@- page @> of <@- totalPages @></td>
	<td colspan="2" class="prevnext"><a href="#" id="recentchecklist-prev">&lt;&nbsp;Previous</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" id="recentchecklist-next">Next&nbsp;&gt;</a></td>
</script>

<script type="text/template" id="recent-checklist-noresult-template">
<td colspan="4" class="noresults">No recently executed checklists found.</td>
</script>
