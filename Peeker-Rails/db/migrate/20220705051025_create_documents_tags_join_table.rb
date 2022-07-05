class CreateDocumentsTagsJoinTable < ActiveRecord::Migration[7.0]
  def change
    create_join_table :documents, :tags, column_options: { type: :uuid } do |t|
      t.index :document_id
      t.index :tag_id
    end
  end
end
