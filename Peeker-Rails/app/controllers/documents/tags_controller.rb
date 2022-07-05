class Documents::TagsController < ApplicationController
  before_action :set_document, :set_tag

  def update
    @document.toggle_tag(@tag)
    render json: { document: @document, tag: @tag }
  end

  private
    def set_document
      @document = Current.user.documents.find(params[:document_id])
    end

    def set_tag
      @tag = Current.user.tags.find(params[:id])
    end
end
