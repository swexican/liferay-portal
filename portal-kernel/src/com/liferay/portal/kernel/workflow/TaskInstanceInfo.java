/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.kernel.workflow;


import java.util.Date;
import java.util.Map;



/**
 * <a href="TaskInstanceInfo.java.html"><b><i>View Source</i></b></a>
 * 
 * <p>
 * This interface represents the information of a workflow task which is
 * requested through the {@link TaskInstanceManager} where you will find
 * additional information about tasks and how to handle them.<br/>
 * A task is always related to a workflow instance (represented through a
 * {@link WorkflowInstanceInfo}) and breaks the execution of the workflow
 * execution plan until the task has been completed. Consequently a task is an
 * element within a workflow definition to break the execution and let the user
 * do some work like making a decision or providing additional information to be
 * used by the continuation of the workflow execution after the task has been
 * completed.<br/>
 * A task can have several meta information usually being presented to the user
 * in order to be completed. The type of meta information is somewhat dependent
 * on the underlying workflow definition language and designer.
 * </p>
 * 
 * @author Micha Kiener
 * 
 */
public interface TaskInstanceInfo {
    /**
     * Returns the unique task instance id which is usually the same as the
     * primary key of the task entity within the database.
     * 
     * @return the id of this task
     */
    long getTaskInstanceId();

    /**
     * Returns the human readable name of this task which is usually given by
     * the workflow definition defining this task. There is no need of this name
     * being unique within the workflow definition, but it is good practice to
     * do so.
     * 
     * @return the name of this task
     */
    String getTaskName();

    /**
     * Returns the task meta information id which is usually a technical name of
     * the workflow definition and thus must be unique within one workflow
     * definition. Usually there is exactly a one to one mapping between the
     * task name and the task meta id, is just a different naming pattern
     * usually. This meta id is usually used to map it to an additional
     * description or even some other related content as it truly represents a
     * unique meta id.
     * 
     * @return the task meta id
     */
    String getTaskMetaId();

    /**
     * Returns the description of this task which is usually used to be
     * presented to the user completing this task. It is optional and thus this
     * method could return <code>null</code>.
     * 
     * @return the description of this task or <code>null</code> if not provided
     */
    String getDescription();

    /**
     * Returns the date and time this task was created (usually by a task
     * executor triggered by the engine).
     * 
     * @return the creation time and date
     */
    Date getCreationDate();

    /**
     * If this task has been completed already (thus {@link #isCompleted()}
     * returns <code>true</code>), this method returns the date of completion.
     * 
     * @return the date and time when this task has been completed or
     *         <code>null</code> if not yet completed
     */
    Date getCompletionDate();

    /**
     * Returns the due date of this task or <code>null</code>, if there is no
     * such date specified.
     * 
     * @return the date where this task has to be completed
     */
    Date getDueDate();

    /**
     * Returns <code>true</code>, if this task has been completed and the
     * process was triggered accordingly.
     * 
     * @return <code>true</code>, if this task has been completed
     */
    boolean isCompleted();

    /**
     * Returns the workflow instance id to which this task belongs. As soon as
     * this task will be completed, the execution of the workflow continues with
     * exactly that workflow instance.
     * 
     * @return the id of the workflow instance this task belongs to
     */
    long getWorkflowInstanceId();

    /**
     * Returns the workflow definition id this task is related to. By default,
     * this is the same as the current one of the workflow instance being
     * referenced by this task, but could have been changed on the workflow
     * instance meanwhile (e.g. this is an asynchronous task and meanwhile the
     * workflow has been moved forward on the related workflow instance where it
     * will not reference the same workflow definition again and hence the task
     * has to know the related workflow definition on its own).
     * 
     * @return the workflow definition id this task is defined in
     */
    String getWorkflowDefinitionId();

    /**
     * Returns the version of the workflow definition this task was defined in.
     * See {@link #getWorkflowDefinitionId()} for more information about
     * referencing the workflow definition.
     * 
     * @return the version of the workflow definition
     */
    int getWorkflowDefinitionVersion();

    /**
     * Returns optional attributes of this task which are dependent on the
     * underlying engine. This map of attributes must not be confused with the
     * one being returned by the workflow instance through
     * {@link WorkflowInstanceInfo#getContextInfo()}.
     * 
     * @return the optional map of attributes related to this task, might be
     *         empty or even <code>null</code>
     */
    Map<String, Object> getAttributes();

    /**
     * If this task has been assigned to a specific user already, its user id is
     * being returned by this method, otherwise <code>null</code> is returned.
     * 
     * @return the id of the user being assigned to this task or
     *         <code>null</code> if not assigned yet
     */
    Long getAssigneeId();

    /**
     * If this task has been assigned to a group or role, it is returned by this
     * method. The interpretation of the returned id is dependent on the usage
     * of the workflow assignment system and is the same as being provided while
     * assigning a task to a group through the {@link TaskInstanceManager}.
     * 
     * @return the group this task has been assigned to or <code>null</code>, if
     *         not assigned yet
     */
    String getAssignedGroup();
}
