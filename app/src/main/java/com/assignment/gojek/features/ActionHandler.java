package com.assignment.gojek.features;

import com.assignment.gojek.models.GitRepo;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
interface ActionHandler {
	void expand(GitRepo repo);
}
