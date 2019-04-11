package org.ccrto.openapi.messaging;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.ccrto.openapi.core.CcrtoPropertyCase;
import org.ccrto.openapi.core.Context;
import org.ccrto.openapi.core.ContextRequestProperties;
import org.ccrto.openapi.core.adapters.ContextRequestPropertiesAdapter;
import org.ccrto.openapi.core.utils.CcrtoJAXBUnmarshalHelper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement
@XmlType(name = "SaveRequest", propOrder = { "context", "data", "options" })
public class SaveRequest {

	@JsonProperty(required = true)
	@XmlElement(required = true, nillable = false)
	private Context context;

	/**
	 * dodatkowe parametry wykorzystywane wewnętrznie przez system, do którego
	 * realizowane jest żądanie.
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlElement(required = false)
	@XmlJavaTypeAdapter(ContextRequestPropertiesAdapter.class)
	protected ContextRequestProperties options;

	private List<CcrtoPropertyCase> data;

	/**
	 * @return the {@link #context}
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * @param context
	 *            the {@link #context} to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * @return the {@link #options}
	 */
	public ContextRequestProperties getOptions() {
		return options;
	}

	/**
	 * @param options
	 *            the {@link #options} to set
	 */
	public void setOptions(ContextRequestProperties options) {
		this.options = options;
	}

	/**
	 * @return the {@link #data}
	 */
	@XmlElement(required = true, nillable = false)
	public List<CcrtoPropertyCase> getData() {
		if (data == null) {
			data = new ArrayList<>();
		}
		return data;
	}

	/**
	 * @param data
	 *            the {@link #data} to set
	 */
	public void setData(List<CcrtoPropertyCase> data) {
		this.data = CcrtoJAXBUnmarshalHelper.unmarshalCcrtoPropertyCaseList(this.context, data, "data");
	}

	public void addData(CcrtoPropertyCase caseObject) {
		getData().add(caseObject);
	}

}
