class DocumentsController < ApplicationController
  before_action :set_document, only: %i[ show update destroy ]

  # GET /documents
  # GET /documents.json
  def index
    @documents = Current.user.documents
  end

  def favorites
    @documents = Current.user.documents.favorites
  end

  # GET /documents/1
  # GET /documents/1.json
  def show
  end

  # POST /documents
  # POST /documents.json
  def create
    @document = Current.user.documents.new(document_params)

    if @document.save
      render :show, status: :created, location: @document
    else
      render json: @document.errors, status: :unprocessable_entity
    end
  end

  # PATCH/PUT /documents/1
  # PATCH/PUT /documents/1.json
  def update
    if @document.update(document_params)
      render :show, status: :ok, location: @document
    else
      render json: @document.errors, status: :unprocessable_entity
    end
  end

  # DELETE /documents/1
  # DELETE /documents/1.json
  def destroy
    @document.destroy
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_document
      @document = Current.user.documents.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def document_params
      params.require(:document).permit(:name, :description, :document_type, :favorite, :expiration_date, :emission_date, :document, :tag_list, :user_id)
    end
end
